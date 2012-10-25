package onlyfun.js.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import onlyfun.js.dao.CarDao;
import onlyfun.js.model.Car;

/**
 * 车辆管理操作
 */
@Repository
public class CarDaoImpl implements CarDao {
	
	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * 通过教练查找
	 * @param coachId 所属教练id
	 */
	@Transactional
	public List<Car> getCarByCoach(long coachId) {
		return null;
	}

	/**
	 * 通过车的id查找
	 * @param carId 所要查询车辆id
	 */
	@Transactional
	public Car getCarById(long carId) {
		Car car = (Car)this.hibernateTemplate.get(Car.class, carId);
		return car;
	}

	/**
	 * 通过车型查找
	 * @param type 车型
	 */
	@Transactional
	public List<Car> getCarByType(String type) {
		@SuppressWarnings("unchecked")
		List<Car> cars = this.hibernateTemplate.find("from Car c where c.type = '"+type+"'");
		return cars;
	}

	/**
	 * 通过车牌号码查找
	 * @param plateNum车牌号码
	 */
	@Transactional
	public Car getCarByPlateNum(String plateNum) {
		Car car = (Car)this.hibernateTemplate.find("from Car c where c.plateNum = '"+plateNum+"'").get(0);
		return car;
	}

	/**
	 * 通过学生查找
	 * @param stuId 使用本车学生的id
	 */
	@Transactional
	public List<Car> getByStudent(long stuId) {
		return null;
	}

	/**
	 * 更新
	 * @param car 所要更新车的实体类
	 */
	@Transactional
	public void update(Car car) {
		this.hibernateTemplate.update(car);
		
	}

	/**
	 * 添加
	 * @param car 车的实例
	 */
	@Transactional
	public void addCar(Car car) {
		this.hibernateTemplate.save(car);
		
	}
	
	/**
	 * 删除
	 * @param carId 车的id
	 */
	@Transactional
	public void deleteCarById(long carId) {
		Car car = this.getCarById(carId);
		this.hibernateTemplate.delete(car);
		
	}

	@Transactional
	public void deleteCar(Car car) {
		this.hibernateTemplate.delete(car);
		
	}

}
