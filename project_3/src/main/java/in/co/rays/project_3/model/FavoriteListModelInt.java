package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.FavoriteListDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface FavoriteListModelInt {
	
	public long add(FavoriteListDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(FavoriteListDTO dto)throws ApplicationException;
	public void update(FavoriteListDTO dto)throws ApplicationException,DuplicateRecordException;
	public FavoriteListDTO findByPK(long pk)throws ApplicationException;
	public FavoriteListDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(FavoriteListDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(FavoriteListDTO dto)throws ApplicationException;
	
}
