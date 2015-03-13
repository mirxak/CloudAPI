package com.main.cloudapi.dao.base;

/**
 * Created by mirxak on 23.01.15.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.entity.base.BaseEntity;
import com.main.cloudapi.utils.JsonUtils;
import com.main.cloudapi.utils.MainConfig;
import org.hibernate.*;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ObjectType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.*;

/**
 * Базовое DAO содержит логику по добавлению/удалению/получению/редактирвания
 * сущности и заполнению дефолтных полей сущности
 */
public abstract class BaseDAO<T extends BaseEntity> implements BaseDAOI<T> {

    protected int block_size = 50;

    public boolean enableFilter = true;

    public static final String IS_DELETED = "coalesce(is_deleted,0) <> 1";
    public static final int STD_OFFSET = 0;
    public static final int STD_LIMIT = 1000;


    protected T entity;

    public BaseDAO(){}

    public BaseDAO(Class<T> EntityClass, SessionFactory factory) {
        this(EntityClass);
        sessionFactory = factory;
    }

    @Override
    public void evict(Object entity) {
        getSession().evict(entity);
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    Class<T> entityClass;
    private Map<String, Method> methodsMap;
    private String sqlBatchQuery;
    private String sqlValueTemplate;

    public BaseDAO(Class<T> EntityClass) {
        try {
            /*if(userService==null){
                
            }*/

            entityClass = EntityClass;
            entity = EntityClass.newInstance();
            fillEntityMap();

            methodsMap = new HashMap<>();
            fillMethodsMap();
            setSqlBatchQuery();
            setSqlValueTemplate();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        if (entity != null) {
            
        }
    }

    private void fillMethodsMap(){
        for (Method m : entityClass.getMethods()){
            if (//(Modifier.isPublic(m.getModifiers())) &&
                    (m.getName().startsWith("get")) && (m.isAnnotationPresent(Column.class))){
                String fieldName = m.getAnnotation(Column.class).name();
                if (!fieldName.equals("id"))methodsMap.put(fieldName, m);
            }
        }
    }

    private void setSqlBatchQuery(){
        sqlBatchQuery = "insert into " + entity.getTable() + " (";
        for (String key : methodsMap.keySet()){
            sqlBatchQuery += key + ",";
        }
        sqlBatchQuery = sqlBatchQuery.substring(0, sqlBatchQuery.length()-1) + ") values ";
    }

    private void setSqlValueTemplate(){
        sqlValueTemplate = "(";
        for (int i=0; i<methodsMap.size(); i++){
            sqlValueTemplate += "?, ";
        }
        sqlValueTemplate = sqlValueTemplate.substring(0, sqlValueTemplate.length()-2) + ")";
    }

    protected SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * получет текущую сессию
     *
     * @return сессия
     */
    protected Session getSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
//            
        } catch (Exception e) {
            session = sessionFactory.openSession();
//            
        }

        return session;
    }

    private String sqlSelectAll;
    private String sqlSelectIds;
    private static int defaultLimit;

    /**
     * @param cl класс сущности
     * @return список сущностей
     * @see BaseDAO#getAll(Class, int, int)
     */

    @Override
    @Deprecated
    public List<T> getAll(Class<T> cl) {
        return getAll(cl, 0, getDefaultLimit());
    }

    @Override
    @Deprecated
    public List<T> getAll(Class<T> cl, Criteria criteria) {
        return getAll(cl, 0, getDefaultLimit(), criteria);
    }

    /**
     * вернет список сущностей
     *
     * @param cl     -класс сущности
     * @param offset -смещение
     * @param limit  - макс количество
     * @return List<Сущности>
     * @see BaseDAO#getAll(Class)
     */
    @Override
    public List<T> getAll(Class<T> cl, int offset, int limit) {
        return getAll(cl, offset, limit, null);
    }

    @Override
    public List<T> getAll(int offset, int limit) {
        return getAll(entityClass, offset, limit, null);
    }

    @Override
    public List<T> getAll(int offset, int limit, Criteria criteria) {
        return getAll(entityClass, offset, limit,criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll(Class<T> cl, int offset, int limit, Criteria criteria) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 0) {
            limit = 0;
        }
        
        if (sqlSelectAll == null) {
            sqlSelectAll = "from " + cl.getName() + " where coalesce(is_deleted,0) <> 1 order by id";
        }

        Session session = getSession();

        if (criteria != null) {
            criteria.add(Restrictions.sqlRestriction("coalesce(this_.is_deleted,0) <> 1"));
            criteria.setMaxResults(limit);
            criteria.setFirstResult(offset);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.addOrder(Order.asc("id"));
            criteria.setReadOnly(true);
            return criteria.list();
        } else {
            Query query = session.createQuery(sqlSelectAll);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            query.setReadOnly(true);
            return query.list();
        }

    }

    /*
        Добавление списка сущностей блоками. Размер блока указывается в переменной block_size
     */
    @Override
    public List<T> batchInsert(List<T> entities){
        if ((entities == null) || (entities.isEmpty()))return Collections.emptyList();

        int i = 0;
        User user = getCurrentUser();
        Session session = getSession();
        while(i < entities.size()){

            StringBuilder sb=new StringBuilder(sqlBatchQuery);
            int end_id = (entities.size() < i+block_size) ? entities.size() : i+block_size;
            for (int j=i; j<end_id; j++){
                sb.append(sqlValueTemplate);
                if (end_id - 1 != j) {
                    sb.append(" , ");
                }
            }
            sb.append("returning id;");

            
            String query=sb.toString();
//            Session session = getSession();
            SQLQuery sqlQuery = session.createSQLQuery(query)
                    .addScalar("id", LongType.INSTANCE);

            int position = 0;
            for (int j=i; j<end_id; j++){
                if (user.getId() != null){
                    entities.get(j).setCreatedBy(user.getId());
                }
                entities.get(j).setCreatedDate(System.currentTimeMillis());

                for (Map.Entry<String, Method> entry : methodsMap.entrySet()){
                    Class classType = entry.getValue().getReturnType();

                    Object value = null;
                    try {
                        value = entry.getValue().invoke(entities.get(j));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    if (value == null){
                        if (classType.equals(Long.class)){
                            sqlQuery.setParameter(position, null, LongType.INSTANCE);
                        }
                        else if (classType.equals(Integer.class)){
                            sqlQuery.setParameter(position, null, IntegerType.INSTANCE);
                        }
                        else if (classType.equals(String.class)){
                            sqlQuery.setParameter(position, null, StringType.INSTANCE);
                        }
                        else{
                            sqlQuery.setParameter(position, null, ObjectType.INSTANCE);
                        }
                    }
                    else{
                        sqlQuery.setParameter(position, value);
                    }
                    position++;
                }
            }


            List<Long> ids = sqlQuery.list();
            if ((ids == null) || (ids.isEmpty())){
                throw new ThrowFabric.BadRequestException("Batch insert error " + entityClass.getSimpleName());
            }

            i += block_size;
        }

        return entities;
    }

//    @Override
//    public User getCurrentUser() {
//        return userService.getCurUser();
//    }

    @Override
    public List<T> addList(List<T> entities) {

        for (T entity : entities) {
            entity.setId(null);
            User u = getCurrentUser();
            if (u != null) {
                entity.setCreatedBy(u.getId());

            }
            entity.setCreatedDate(getCurrentSqlDate().getTime());
            entity.setModifiedDate(null);
            entity.setModifiedUser(null);
            Session session = getSession();
            session.save(entity);
        }

        return entities;
    }

    /**
     * обновляет сущность
     *
     * @param entity сущность
     */
    @Override
    public T update(T entity) {
        User u = getCurrentUser();
        if (u != null) {
            entity.setModifiedUser(u.getId());
        }
        entity.setModifiedDate(getCurrentSqlDate().getTime());


        return updateSimple(entity);
    }

    @SuppressWarnings("unchecked")
    protected T updateSimple(T entity) {
        Session session = getSession();
        entity = (T) session.merge(entity);
        session.evict(entity);

        session.update(entity);
        session.flush();     //без этого говна не работает (причина во вью фильтер для лейзи запроса)
        return entity;
    }

    /**
     * @return текущую дата (временная функуция)
     */
    @Deprecated
    public static Date getCurrentSqlDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * "удаляет" сущность (ставит отметку IsDeleted=1)
     *
     * @param entity - сущность
     */
    @Override
    public void delete(T entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            throw new ThrowFabric.BadRequestException("entity doesn't exist " + entity.getClass().getSimpleName() + " id=" + entity.getId());
        }
        entity.setDeletedDate(getCurrentSqlDate().getTime());
        entity.setModifiedDate(entity.getDeletedDate());
        entity.setIsDeleted(1);
        entity.setDeletedBy(null);
        Session session = getSession();
        evict(entity);
        session.update(entity);
        session.flush();     //без этого говна не работает (причина во вью фильтер для лейзи запроса)
    }

    public List<T> deleteGroup(List<T> entities){
        if ((entities == null) || (entities.isEmpty()))return Collections.emptyList();

        User user=getCurrentUser();
        String query = "update " + entity.getTable() + " set deleted_date=:deleted_date, last_modified_date=:modified_date,deleted_by=:deleted_by, is_deleted=1 where id in :ids";
        int i=0;
        Session session = getSession();
        while(i < entities.size()){
            List<Long> ids =  new ArrayList<>();
            int end_id = (entities.size() < i+block_size) ? entities.size() : i+block_size;

            Long deleteDate = System.currentTimeMillis();

            for (int j=i; j<end_id; j++){
                ids.add(entities.get(j).getId());
                entities.get(j).setDeletedDate(deleteDate);
                entities.get(j).setModifiedDate(deleteDate);
                entities.get(j).setIsDeleted(1);
                entities.get(j).setDeletedBy(user.getId());
            }


            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("deleted_date", deleteDate);
            sqlQuery.setParameter("modified_date", deleteDate);
            sqlQuery.setParameter("deleted_by", user.getId(),LongType.INSTANCE);
            sqlQuery.setParameterList("ids", ids);
            sqlQuery.executeUpdate();
            i += block_size;
        }


        return entities;
    }


    @Override
    public T getById(Long id) {
        return getById(id, entityClass);
    }
    protected String sqlSelectId;
    /**
     * верент сущность по ID
     *
     * @param id -запрашиваемый id
     * @param cl - класс сущности
     * @return если не найдено вернет новый пустой объект сущности
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getById(Long id, Class<T> cl) {
        if (id == null || id <= 0) {
            return entity;
        }
        Session session = getSession();
        if (sqlSelectId == null) {
            sqlSelectId = "from " + cl.getName() + " where coalesce(is_deleted,0) <> 1 and id=:id";
        }
        Query q = session.createQuery(sqlSelectId);
        q.setLong("id", id);
        List<T> l = q.list();
        if (l.isEmpty()) {
            return entity;
        } else {
            return l.get(0);
        }
    }

    @Override
    public T getById(Long id, boolean throwIfNotExist, Criteria criteria) {
        return getById(id,entityClass,throwIfNotExist,criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getById(Long id, Class<T> cl, boolean throwIfNotExist, Criteria criteria) {

        if (criteria != null) {
            criteria.add(Restrictions.sqlRestriction("coalesce(this_.is_deleted,0) <> 1"));
            criteria.add(Restrictions.eq("id", id));
            T en = (T) criteria.uniqueResult();

            if (en == null) {
                if (throwIfNotExist) {
                    throwDonNotExist(id);
                } else {
                    en = entity;
                }
            }
            return en;
        } else {
            return getById(id, cl, throwIfNotExist);
        }
    }

    @SuppressWarnings("unchecked")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    @Override
    public T loadById(Long id, Class<T> cl) {
        if (id == null || id <= 0) {
            return entity;
        }
        Session session = getSession();
        T ent = (T) session.load(cl, id);
        if (ent == null) {
            return entity;
        } else {
            return ent;
        }
    }




    @Override
    public T getById(Long id, boolean throwIfNotExist) {
        return getById(id,entityClass,throwIfNotExist);
    }

    @Override
    public T getById(Long id, Class<T> cl, boolean throwIfNotExist) {

        if (id == null && throwIfNotExist) {
            throwDonNotExist(cl, null);
        }
        T t = getById(id, cl);
        if (throwIfNotExist) {
            if (t.getId() == null) {
                throwDonNotExist(cl, id);
            }
        }
        return t;
    }


    protected void throwDonNotExist(Class<T> cl, Long id) {
        throw new ThrowFabric.BadRequestException("entity doesn't exist " + cl.getSimpleName() + " id=" + id);
    }

    public void throwDonNotExist(Object id) {
        throw new ThrowFabric.BadRequestException("entity doesn't exist " + entity.getClass().getSimpleName() + " id=" + id);
    }


    public static void throwDonNotExist(Class T, Object id) {
        throw new ThrowFabric.BadRequestException("entity doesn't exist " + T.getSimpleName() + " id=" + id);
    }

    /**
     * @return дефолтный лимит
     */
    public static int getDefaultLimit() {
        if (defaultLimit == 0) {
            String strLim = MainConfig.get("sql.settings.limit");
            try {
                defaultLimit = Integer.valueOf(strLim);
            } catch (Exception e) {
                defaultLimit = 1000;
            }
        }

        return defaultLimit;
    }


    private static int defaultLimitMAX;

    /**
     * @return дефолтный максимум
     */
    private static int getDefaultLimitMax() {
        if (defaultLimitMAX == 0) {
            String strLim = MainConfig.get("sql.settings.limit.max");
            try {
                defaultLimitMAX = Integer.valueOf(strLim);
            } catch (Exception e) {
                defaultLimitMAX = 10000;     //а вот не будет отдавать больше 50к объектов ибо нефиг (если надо ставь проперти в настройках)
            }
        }
        return defaultLimitMAX;
    }

    /**
     * вернет список сущностей
     *
     * @param ids -запрашиваемые Id
     * @param cl  - класс сушности
     * @return @return List<Сущности>
     */
    @Deprecated
    @Override
    public List<T> getByIds(Set<Long> ids, Class<T> cl) {
        return getByIds(ids, cl, 0, getDefaultLimit());
    }


    @Override
    public List<T> getByIds(Set<Long> ids, int offset, int limit) {
        return getByIds(ids,entityClass,offset,limit);
    }

    /**
     * вернет список сущностей
     *
     * @param ids    -запрашиваемые Id
     * @param cl     - класс сушности
     * @param offset -смещение
     * @param limit  -колисество
     * @return List<Сущности>
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getByIds(Set<Long> ids, Class<T> cl, int offset, int limit) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            limit = limit(limit);
            if (sqlSelectIds == null) {
                sqlSelectIds = "from " + cl.getName() + " where coalesce(is_deleted,0) <> 1 and id in(:list)";
            }
            Session session = getSession();
            Query query = session.createQuery(sqlSelectIds);
            query.setParameterList("list", ids);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return (List<T>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private int limit(int limit) {
        return limit > getDefaultLimitMax() ? getDefaultLimitMax() : limit;
    }

    /**
     * добавляет в Бд сущность
     *
     * @param entity - сущность
     */
    @Override
    public void add(T entity) {
        entity.setId(null);
        User u = getCurrentUser();
        if (u != null) {
            entity.setCreatedBy(u.getId());

        }
        entity.setCreatedDate(getCurrentSqlDate().getTime());
        entity.setModifiedDate(null);
        entity.setModifiedUser(null);

        Session session = getSession();
        session.save(entity);
    }

    @Override
    public Criteria createCriteria() {
        return getSession().createCriteria(entityClass);
    }


//    @PostConstruct
//    protected void init() {
//        if (defaultController != null) {
//
//            if (entity instanceof BaseEntity) {
//                BaseEntity ent = (BaseEntity) entity;
//                SecurityEnable an = entity.getClass().getAnnotation(SecurityEnable.class);
//                if (an != null && an.value()) {
//
//                    defaultController.addEntity(ent);
//                }
//            }
//        }
//    }


    private Map<String, Pair<String, Type>> entityMap = new HashMap<>();
//private final HashMap<String, Type> entityMap = new HashMap<>();

    /**
     * идем по текщей сущности
     * ищем все поля с антоацией Column без JsonIgnore
     * типа Integer Long String
     * по ним можно будет фильтровать
     */
    private void fillEntityMap() {
        entityMap=getEntityMap(entity);
        
    }

    public static Map<String,Pair<String,Type>> getEntityMap(Object entity){
        //TODO есть бага (при наследовании классов) видит гетеры но не видит поля
        //исправляется получением фиелдов родительского класса


        Map<String,Pair<String,Type>> res=new HashMap<>();
        try {
            Class entClazz = entity.getClass();
            // Annotation an = entClazz.getAnnotation(MappedSuperclass.class);
            while (entClazz != null) {

                for (Method m : entClazz.getDeclaredMethods()) {
                    //Annotation[] annotations = m.getDeclaredAnnotations();
                    if (//Modifier.isPublic(m.getModifiers()) &&
                            m.getName().startsWith("get") &&
                                    !m.isAnnotationPresent(JsonIgnore.class) &&
                                    m.isAnnotationPresent(Column.class)
                            ) {
                        //List<Annotation> list= Arrays.asList(annotations);
                        String fieldName = m.getName().substring(3);
                        Field[] fields = entClazz.getDeclaredFields();

                        for (Field f : fields) {
                            if (f.getName().equalsIgnoreCase(fieldName)) {
                                if (f.getType() == Long.class ||       //пока добавим в фильтры только 3 этих типа
                                        f.getType() == String.class ||
                                        f.getType() == Integer.class) {


                                    String lowerCase = JsonUtils.toLOWER_CASE_WITH_UNDERSCORES(f.getName());
                                    res.put(lowerCase, new Pair<String, Type>(f.getName(), f.getType()));
                                }
                                break;
                            }
                        }
                    }
                }

                entClazz = entClazz.getSuperclass();
                Annotation an = entClazz.getAnnotation(MappedSuperclass.class);
                if (an == null) {
                    entClazz = null;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return res;
    }


    private static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(){}
        public Pair(K key, V value){
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

//endregion
}
