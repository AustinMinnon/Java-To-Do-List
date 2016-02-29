import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Task {
  private int id;
  private String description;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Task(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }


  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String description) {
    this.description = description;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", this.description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM tasks WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM categories_tasks WHERE task_id = :taskId";
        con.createQuery(joinDeleteQuery)
          .addParameter("taskId", this.getId())
          .executeUpdate();
    }
  }

  public void addCategory(Category category) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id)";
      con.createQuery(sql)
        .addParameter("category_id", category.getId())
        .addParameter("task_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Category> getCategories() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT category_id FROM categories_tasks WHERE task_id = :task_id";
      List<Integer> categoryIds = con.createQuery(sql)
        .addParameter("task_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Category> categories = new ArrayList<Category>();

      for (Integer categoryId : categoryIds) {
          String taskQuery = "Select * From categories WHERE id = :categoryId";
          Category category = con.createQuery(taskQuery)
            .addParameter("categoryId", categoryId)
            .executeAndFetchFirst(Category.class);
          categories.add(category);
      }
      return categories;
    }
  }

  public static List<Task> getCombinedTasks(String[] categoriesids){
    String sql = "SELECT DISTINCT task_id FROM categories_tasks WHERE category_id = ";
    for(int i = 0; i < categoriesids.length; i++){
      if (i == 0) {
        sql = sql + categoriesids[0];
      }else {
        sql += " OR category_id = " + categoriesids[i];
      }
    }

    try(Connection con = DB.sql2o.open()){
      List<Integer> taskIds = con.createQuery(sql)
        .executeAndFetch(Integer.class);

      ArrayList<Task> tasks = new ArrayList<Task>();

      for (Integer taskId : taskIds) {
        String taskQuery = "SELECT * FROM tasks WHERE id = :taskId";
        Task task = con.createQuery(taskQuery)
          .addParameter("taskId", taskId)
          .executeAndFetchFirst(Task.class);
        tasks.add(task);
      }
    return tasks;
    }
  }

  public static List<Task> getSharedTasks(String[] categoriesids){
    String sql = "SELECT task_id FROM categories_tasks WHERE category_id IN (";
    for(int i = 0; i < categoriesids.length; i++){
      if (i == 0) {
        sql = sql + categoriesids[0];
      }else {
        sql += ", " + categoriesids[i];
      }
    }
    sql += ") GROUP BY task_id HAVING count(*) = " + categoriesids.length;

    try(Connection con = DB.sql2o.open()){
      List<Integer> taskIds = con.createQuery(sql)
        .executeAndFetch(Integer.class);

      ArrayList<Task> tasks = new ArrayList<Task>();

      for (Integer taskId : taskIds) {
        String taskQuery = "SELECT * FROM tasks WHERE id = :taskId";
        Task task = con.createQuery(taskQuery)
          .addParameter("taskId", taskId)
          .executeAndFetchFirst(Task.class);
        tasks.add(task);
      }
    return tasks;
    }
  }
}
