import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn");
    Task secondTask = new Task("Mow the lawn");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertTrue(savedTask.equals(myTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void update_updatesTaskDescription() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    myTask.update("Mow all the lawns");
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
    assertEquals(myTask.getDescription(), "Mow all the lawns");
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void addCategory_addsCategoryToTask() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    Category savedCategory = myTask.getCategories().get(0);
    assertTrue(myCategory.equals(savedCategory));
  }

  @Test
  public void getCategories_returnsAllCategories_ArrayList() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    List savedCategories = myTask.getCategories();
    assertEquals(savedCategories.size(), 1);
  }

  @Test
  public void getCombinedTasks_returnsTasksFromDifferentCategories(){
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
    task1.addCategory(category1);
    task1.addCategory(category2);
    task2.addCategory(category1);
    task3.addCategory(category2);
    String[] categoriesids = {Integer.toString(category1.getId()), Integer.toString(category2.getId()) };
    List combinedTasks = Task.getCombinedTasks(categoriesids);
    assertEquals(combinedTasks.size(), 3);
    assertTrue(combinedTasks.contains(task3));
  }

  @Test
  public void getSharedTasks_returnsSharedTasksFromMultipleCategories() {
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
    String[] categoriesids = {Integer.toString(category1.getId()), Integer.toString(category2.getId()) };
    List sharedTasks = Task.getSharedTasks(categoriesids);
    assertEquals(sharedTasks.size(), 2);
    assertFalse(sharedTasks.contains(task3));
  }

  @Test
  public void delete_deletesAllTasksAndListsAssoicationes() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    myTask.delete();
    assertEquals(myCategory.getTasks().size(), 0);
  }

  @Test
  public void getStatus_returnsCompleteStatus() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    assertFalse(myTask.getStatus());
  }

  @Test
  public void completed_ChangeStatusToTrue() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();
    myTask.addCategory(myCategory);
    myTask.completed();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(savedTask.getStatus());
  }

}
