package onlyfun.js.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import onlyfun.js.dao.StudentDao;
import onlyfun.js.model.Student;

/**
 * @author ji
 * 
 */
@Repository
public class StudentDaoImpl implements StudentDao {

	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Transactional
	public List<Student> getStudent() {
		@SuppressWarnings("unchecked")
		List<Student> students = this.hibernateTemplate.find("from Student");
		return students;
	}

	@Transactional
	public Student getStudentById(long stuId) {
		Student stu = (Student) this.hibernateTemplate
				.get(Student.class, stuId);
		return stu;
	}

	@Transactional
	public void update(Student stu) {
		this.hibernateTemplate.update(stu);
	}

	@Transactional
	public void deleteStudent(Student stu) {
		this.hibernateTemplate.delete(stu);
	}

	@Transactional
	public void deleteStudentById(long stuId) {
		Student stu = (Student) this.hibernateTemplate
				.get(Student.class, stuId);
		this.hibernateTemplate.delete(stu);
	}

	@Transactional
	public void addStudent(Student stu) {
		this.hibernateTemplate.save(stu);
	}

	@Transactional
	public Student getStuByUsername(String username) {
		@SuppressWarnings("unchecked")
		List<Student> stu = this.hibernateTemplate
				.find("from Student stu where stu.username='" + username + "'");
		return (stu.size() == 0 || stu.get(0) == null) ? null : stu.get(0);
	}

	@Transactional
	public boolean isExist(String username) {
		@SuppressWarnings("unchecked")
		List<String> stu = this.hibernateTemplate
				.find("select stu.username from Student stu where stu.username='"
						+ username + "'");
		return (stu.size() != 0 || stu.get(0) != null);
	}

	@Transactional
	public boolean login(String username, String password) {
		String sql = "from Student stu where stu.username=? and stu.password=?";
		String[] values = new String[] { username, password };
		@SuppressWarnings("unchecked")
		List<Student> stu = this.hibernateTemplate.find(sql, values);
		return (stu.size() != 0);
	}

	@Transactional
	public List<Object[]> getStudentsList() {
		@SuppressWarnings("unchecked")
		List<Object[]> list = this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "SELECT personId,name,dateOfEntry,coachId FROM person WHERE discriminator='student'";
				Query q = session.createSQLQuery(sql);
				return q.list();
			}
		});
		return list;
	}
}