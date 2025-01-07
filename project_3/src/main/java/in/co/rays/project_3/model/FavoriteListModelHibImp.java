package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.FavoriteListDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public abstract class FavoriteListModelHibImp implements FavoriteListModelInt {

	@Override
	public long add(FavoriteListDTO dto) throws ApplicationException, DuplicateRecordException {

		FavoriteListDTO existDto = null;

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			session.save(dto);

			dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in  FavoriteListDTO Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(FavoriteListDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in  FavoriteListDTO Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(FavoriteListDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;

		/*
		 * Transaction tx = null; FavoriteListDTO exesistDto =
		 * findByLogin(dto.getLogin());
		 * 
		 * if (exesistDto != null && exesistDto.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Login id already exist"); }
		 * 
		 */ Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in  FavoriteListDTO update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public FavoriteListDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		FavoriteListDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (FavoriteListDTO) session.get(FavoriteListDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Bank by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public FavoriteListDTO findByLogin(String login) throws ApplicationException {

		Session session = null;
		FavoriteListDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FavoriteListDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (FavoriteListDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting  FavoriteListDTO by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FavoriteListDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Banks list");
		} finally {
			session.close();
		}

		return list;
	}

	/*
	 * @Override public List list(int pageNo, int pageSize) throws
	 * ApplicationException { // TODO Auto-generated method stub return null; }
	 */
	@Override
	public List search(FavoriteListDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<FavoriteListDTO> list = null;
		try {
			session = HibDataSource.getSession();
			System.out.println("---------------------------------");
			Criteria criteria = session.createCriteria(FavoriteListDTO.class);
			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getProduct() != null && dto.getProduct().length() > 0) {
					criteria.add(Restrictions.like("product", dto.getProduct() + "%"));
				}
				if (dto.getAddedDate() != null && dto.getAddedDate().getDate() > 0) {
					criteria.add(Restrictions.eq("addedDate", dto.getAddedDate()));
				}

				if (dto.getUserName() != null && dto.getUserName().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
				}

			
				if (dto.getComment() != null && dto.getComment().length() > 0) {
					criteria.add(Restrictions.like("comment", dto.getComment() + "%"));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<FavoriteListDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in  FavoriteListDTO search");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(FavoriteListDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

}