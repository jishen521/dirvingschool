package onlyfun.js.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class StudyItems {
	private Long id;
	private String itemName;
	private String classHour;
	private Set<Coach> coach = new HashSet<Coach>();

	@Id
	@GeneratedValue
	@Column(name = "itemId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getClassHour() {
		return classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	@ManyToMany
	@JoinTable(name = "item_coach", joinColumns = { @JoinColumn(name = "itemId") }, inverseJoinColumns = { @JoinColumn(name = "coachId") })
	public Set<Coach> getCoach() {
		return coach;
	}

	public void setCoach(Set<Coach> coach) {
		this.coach = coach;
	}
}
