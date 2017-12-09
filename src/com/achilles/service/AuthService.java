package com.achilles.service;

import java.text.ParseException;
import java.util.regex.Pattern;

import com.achilles.dao.AdminDAO;
import com.achilles.dao.impl.AdminDAOImpl;
import com.achilles.model.Admin;


public class AuthService {

	private Pattern regAction = Pattern.compile("/.*action$");
	private Pattern regLoginReqHtml = Pattern.compile("/page/.*jsp$");
	private Pattern regOrganizationReqMenuHtml = Pattern.compile("/page/menu/organization.html");
	private Pattern regOrganizationReqHtml = Pattern.compile("/page/organization/.*html$");
	private Pattern regUserReqMenuHtml = Pattern.compile("/page/menu/user.html");
	private Pattern regUserReqHtml = Pattern.compile("/page/user/.*html$");
	private Pattern regGroupReqMenuHtml = Pattern.compile("/page/menu/group.html");
	private Pattern regGroupReqHtml = Pattern.compile("/page/group/.*html$");
	private Pattern regRoleReqMenuHtml = Pattern.compile("/page/menu/role.html");
	private Pattern regRoleReqHtml = Pattern.compile("/page/role/.*html$");
	private Pattern regResourceReqMenuHtml = Pattern.compile("/page/menu/resource.html");
	private Pattern regResourceReqHtml = Pattern.compile("/page/resource/.*html$");
	private Pattern regPrivilegeReqMenuHtml = Pattern.compile("/page/menu/privilege.html");
	private Pattern regPrivilegeReqHtml = Pattern.compile("/page/privilege/.*html$");
	private Pattern regSystemReqMenuHtml = Pattern.compile("/page/menu/system.html");
	private Pattern regSystemReqHtml = Pattern.compile("/page/system/.*html$");
	private Pattern regLogReqMenuHtml = Pattern.compile("/page/menu/log.html");
	private Pattern regLogReqHtml = Pattern.compile("/page/log/.*html$");
	private Pattern regAdminReqMenuHtml = Pattern.compile("/page/menu/admin.html");
	private Pattern regAdminReqHtml = Pattern.compile("/page/admin/.*html$");
	
	public boolean CheckStatus(String loginid) throws Exception 
	{
		AdminDAO dao = new AdminDAOImpl();
		Admin admin = null;
		try {
			admin = dao.GetAdminById(loginid);
			
			if(admin  == null)
			{
				return false;
			}
//			if(Admin.FROZEN == admin.getStatus())
//			{
//				String frozentime = admin.getFrozentime();
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
//						Locale.SIMPLIFIED_CHINESE);
//				sdf.parse(frozentime);
//				
//				Calendar frozen = sdf.getCalendar();
//				Calendar now=Calendar.getInstance();
//				//锁定时间�?分钟后解�?
//				now.add(Calendar.MINUTE, -5);
//				boolean flag = now.after(frozen);
//				if(!flag)
//				{
//					System.out.println("administrator '" + loginid + "' has frozen when " + frozentime);
//					return false;
//				}
//				else
//				{
//					admin.setStatus(Admin.INUSE);
//					dao.AdminMod(admin);
//					return true;
//				}
//			}
			else{
				return true;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}
	}
	
	public boolean LoginPwdService( String loginid, String pwd ) throws Exception
	{
		AdminDAO dao = new AdminDAOImpl();
		Admin admin = null;
		try {
			admin = dao.GetAdminById(loginid);
		
			if(admin  == null)
			{
				return false;
			}
			
			if( admin.getPassword() != null && admin.getPassword().equals(pwd)) {
//				admin.setErrorcount(0);
//				admin.setStatus(Admin.INUSE);
//				dao.AdminMod(admin);
				return true;
			}
			else
			{
//				int count = admin.getErrorcount();
//				//尝试次数超过设定值后锁定管理 员
//				if(count >= 4)
//				{
//					admin.setErrorcount(0);
//					admin.setStatus(Admin.FROZEN);
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
//							Locale.SIMPLIFIED_CHINESE);
//					String timenow = sdf.format(new Date());
//					admin.setFrozentime(timenow);
//					dao.AdminMod(admin);
//				}
//				else
//				{
//					admin.setErrorcount(admin.getErrorcount() + 1);
//					dao.AdminMod(admin);
//				}
			}
		} catch (ParseException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}

}
