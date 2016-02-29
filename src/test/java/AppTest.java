import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  //Integration testing
  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to To Do List");
  }

  @Test
  public void SeeTasksInAnyOfSeveralCategories() {
    Category category1 = new Category("Household chores");
    category1.save();
    Category category2 = new Category("Outdoor chores");
    category2.save();
    Task task1 = new Task("Mow the lawn");
    task1.save();
    Task task2 = new Task("Do the dishes");
    task2.save();
    Task task3 = new Task("Weed the garden");
    task3.save();
    Task task4 = new Task("Paint The Fence");
    task4.save();
    task1.addCategory(category1);
    task1.addCategory(category2);
    task2.addCategory(category1);
    task3.addCategory(category2);
    task4.addCategory(category1);
    task4.addCategory(category2);
    goTo("http://localhost:4567/");
    click("a", withText("Filter Categories"));
    click("#allTasks");
    click("#category" + category1.getId());
    click("#category" + category2.getId());
    submit(".btn");
    assertThat(pageSource()).contains("Mow the lawn");
    assertThat(pageSource()).contains("Paint The Fence");

  }
}
