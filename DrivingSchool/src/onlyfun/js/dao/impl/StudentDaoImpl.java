package onlyfun.js.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
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
	public void setSessionFactory(SessionFactory sessionFactory){
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
		Student stu = (Student)this.hibernateTemplate.get(Student.class, stuId);
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
	public void deleteStudentById(long stuId){
		Student stu = (Student)this.hibernateTemplate.get(Student.class, stuId);
		this.hibernateTemplate.delete(stu);
	}

	@Transactional
	public void addStudent(Student stu) {
		this.hibernateTemplate.save(stu);
	}

}
