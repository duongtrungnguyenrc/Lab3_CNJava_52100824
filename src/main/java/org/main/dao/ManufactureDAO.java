package org.main.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.main.domain.Manufacture;
import org.main.repository.Repository;
import org.main.utils.HibernateUtils;

import java.util.List;

public class ManufactureDAO<T, k> implements Repository<T, k> {
    private Session session = null;

    public ManufactureDAO(){
        this.session = HibernateUtils.getSessionFactory().openSession();
    }

    @Override
    public boolean add(k params) {
        try {
            this.session.save((Manufacture) params);
            this.session.beginTransaction().commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T get(k id) {
        String hql = "From Manufacture where id = :id";
        Query query = this.session.createQuery(hql);
        query.setParameter("id",(int) id);
        Manufacture result = (Manufacture) query.uniqueResult();
        return (T) result;
    }

    @Override
    public List<T> getAll() {
        String hql = "From Manufacture";
        Query query = this.session.createQuery(hql);
        List<Manufacture> manufactures = (List<Manufacture>) query.getResultList();
        return (List<T>) manufactures;
    }

    @Override
    public boolean remove(int id) {
        try {
            String hql = "delete from Manufacture where id = :id";
            Query query = this.session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(T p) {
        try {
            String hql = "delete from Manufacture where id = :id";
            Query query = this.session.createQuery(hql);
            query.setParameter("id", ((Manufacture) p).getId());
            query.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(T p) {
        try {
            String hql = "update Manufacture set name=:name, employee=:employee, location=:location where id=:id";
            Query query = this.session.createQuery(hql);
            query.setParameter("id", ((Manufacture) p).getId());
            query.setParameter("name", ((Manufacture) p).getName());
            query.setParameter("employee", ((Manufacture) p).getEmployee());
            query.setParameter("location", ((Manufacture) p).getLocation());
            query.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean areAllManufacturersAboveEmployeeThreshold() {
        String hql = "SELECT COUNT(*) FROM Manufacture m WHERE m.employee <= 100";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long count = query.uniqueResult();
        return count == 0;
    }

    public int getTotalEmployees() {
        String hql = "SELECT SUM(m.employee) FROM Manufacture m";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long sum = query.uniqueResult();
        return sum != null ? sum.intValue() : 0;
    }

    public Manufacture getLastManufacturerInUS() {
        String hql = "FROM Manufacture m WHERE m.location = 'US' ORDER BY m.id DESC";
        Query<Manufacture> query = session.createQuery(hql, Manufacture.class);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    public boolean isEmptyData() {
        String hql = "SELECT COUNT(*) FROM Manufacture";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long count = query.uniqueResult();
        return count == 0;
    }

    public void closeSession() {
        this.session.close();
    }

}
