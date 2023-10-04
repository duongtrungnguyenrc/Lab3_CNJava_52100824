package org.main.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.main.domain.Phone;
import org.main.repository.Repository;
import org.main.utils.HibernateUtils;

import java.util.List;
public class PhoneDAO<T, k> implements Repository<T, k> {
    private Session session = null;
    private Transaction transaction = null;

    public PhoneDAO(){
        this.session = HibernateUtils.getSessionFactory().openSession();
        this.transaction = this.session.beginTransaction();
    }
    @Override
    public boolean add(k params) {
        this.session.save((Phone) params);
        this.transaction.commit();
        return false;
    }

    @Override
    public T get(k id) {
        String hql = "From Phone where id = :id";
        Query query = this.session.createQuery(hql);
        query.setParameter("id",(int) id);
        Phone result = (Phone) query.uniqueResult();
        return (T) result;
    }

    @Override
    public List<T> getAll() {
        String hql = "From Phone";
        Query query = this.session.createQuery(hql);
        List<Phone> phones = (List<Phone>) query.getResultList();
        return (List<T>) phones;
    }

    @Override
    public boolean remove(int id) {
        try {
            String hql = "delete from Phone where id = :id";
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
            String hql = "delete from Phone where id = :id";
            Query query = this.session.createQuery(hql);
            query.setParameter("id", ((Phone) p).getId());
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
            String hql = "update Phone set name=:name, price=:price, color=:color, country=:country where id=:id";
            Query query = this.session.createQuery(hql);
            query.setParameter("id", ((Phone) p).getId());
            query.setParameter("name", ((Phone) p).getName());
            query.setParameter("color", ((Phone) p).getColor());
            query.setParameter("price", ((Phone) p).getPrice());
            query.setParameter("country", ((Phone) p).getCountry());
            query.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getHighestSellingPrice() {
        try {
            String hql = "FROM Phone p WHERE p.price = (SELECT MAX(price) FROM Phone)";
            Query query = session.createQuery(hql);
            Phone phone = (Phone) query.uniqueResult();
            return phone.getPrice();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Phone> getPhonesSortedByCountryAndPrice() {
        String hql = "FROM Phone p ORDER BY p.country, p.price DESC";
        Query<Phone> query = this.session.createQuery(hql, Phone.class);
        return query.getResultList();
    }

    public boolean isPhonePricedAboveThreshold(double threshold) {
        String hql = "SELECT COUNT(*) FROM Phone p WHERE p.price > :threshold";
        Query<Long> query = this.session.createQuery(hql, Long.class);
        query.setParameter("threshold", threshold);
        Long count = query.uniqueResult();
        return count > 0;
    }

    public Phone getFirstMatchingPhone(String color, double priceThreshold) {
        String hql = "FROM Phone p WHERE p.color = :color AND p.price > :threshold ORDER BY p.id";
        Query<Phone> query = this.session.createQuery(hql, Phone.class);
        query.setParameter("color", color);
        query.setParameter("threshold", priceThreshold);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    public void closeSession() {
        this.session.close();
    }
}
