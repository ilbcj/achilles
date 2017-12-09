package com.achilles.dao;

import com.achilles.model.Admin;

public interface AdminDAO {
	public Admin GetAdminById(String id) throws Exception;
	
//	public List<Admin> GetAllAdmins() throws Exception;
//	public List<Admin> GetAdmins(Admin criteria, String business_type, int start, int length) throws Exception;
//	public int GetAdminCount(Admin criteria, String business_type) throws Exception;
//	public List<AdminBusinessType> GetTypes(AdminBusinessType criteria, int page, int rows) throws Exception;
//	public void AdminAdd(Admin admin) throws Exception;
//	public void AdminMod(Admin admin) throws Exception;
//	public void AdminDel(Admin admin) throws Exception;
//	public Admin AdminsAdd(Admin admin) throws Exception;
//	
//	
//	public BusinessType GetBussinessByCode(String code) throws Exception;
//	public AdminType GetAdminTypeByCode(String code) throws Exception;
//	public void UpdateTypes(String aid, List<String> businessTypes) throws Exception;
//	public List<AdminBusinessType> GetAdminBusinessTypeByAid(String aid) throws Exception;
//	public List<AdminType> GetAllAdminTypes() throws Exception;
//	public List<BusinessType> GetAllBusinessTypes() throws Exception;
}
