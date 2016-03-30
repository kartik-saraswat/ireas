package projectireas.views;

import io.dropwizard.views.View;
import projectireas.core.Person;

public class PersonView extends View{
	private final Person person;

    public PersonView(Person person) {
        super("person.ftl");
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }	
}
