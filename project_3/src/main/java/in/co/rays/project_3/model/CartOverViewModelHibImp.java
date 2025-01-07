package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CartOverViewDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public abstract class CartOverViewModelHibImp implements  CartOverViewModelInt {
	
	@Override
	public long add( CartOverViewDTO dto) throws ApplicationException, DuplicateRecordException {
		
		  CartOverViewDTO existDto = null;
			
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
				throw new ApplicationException("Exception in  CartOverViewDTO Add " + e.getMessage());
			} finally {
				session.close();
			}


		return dto.getId();
	}

	@Override
	public void delete( CartOverViewDTO dto) throws ApplicationException {

		
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
			throw new ApplicationException("Exception in  CartOverViewDTO Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update( CartOverViewDTO dto) throws ApplicationException, DuplicateRecordException {
		
		
		Session session = null;
		
		/*
		 * Transaction tx = null;  CartOverViewDTO exesistDto = findByLogin(dto.getLogin());
		 * 
		 * if (exesistDto != null && exesistDto.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Login id already exist"); }
		 * 
		 */		  Transaction tx = null;
		 

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in  CartOverViewDTO update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public  CartOverViewDTO findByPK(long pk) throws ApplicationException {
		
		
		Session session = null;
		 CartOverViewDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = ( CartOverViewDTO) session.get( CartOverViewDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Bank by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public  CartOverViewDTO findByLogin(String login) throws ApplicationException {
		
		
		
		Session session = null;
		 CartOverViewDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria( CartOverViewDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = ( CartOverViewDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting  CartOverViewDTO by Login " + e.getMessage());

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
			Criteria criteria = session.createCriteria( CartOverViewDTO.class);
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
	public List search( CartOverViewDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList< CartOverViewDTO> list = null;
		try {
			session = HibDataSource.getSession();
			System.out.println("---------------------------------");
			Criteria criteria = session.createCriteria( CartOverViewDTO.class);
			if (dto != null) {
				
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				  
				if (dto.getCustomerName() != null && dto.getCustomerName().length() > 0) {
					criteria.add(Restrictions.like("customerName", dto.getCustomerName() + "%"));
				}

				
				  
				if (dto.getProduct() != null && dto.getProduct().length() > 0) {
					criteria.add(Restrictions.like("product", dto.getProduct() + "%"));
				}
				
				  if (dto.getTransactionDate() != null && dto.getTransactionDate().getDate() > 0) {
						criteria.add(Restrictions.eq("transactionDate", dto.getTransactionDate()));
					}
				  if ( dto.getQuantityOrdered() > 0) {
						criteria.add(Restrictions.eq("quantityOrdered", dto.getQuantityOrdered()));
					}
			  
					
					
			}
					
					if (pageSize > 0) {
						pageNo = (pageNo - 1) * pageSize;
						criteria.setFirstResult(pageNo);
						criteria.setMaxResults(pageSize);
					}
					list = (ArrayList< CartOverViewDTO>) criteria.list();
				} catch (HibernateException e) {
					throw new ApplicationException("Exception in  CartOverViewDTO search");
				} finally {
					session.close();
				}

		
		return list;
	}

	@Override
	public List search( CartOverViewDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0,0);
	}


}