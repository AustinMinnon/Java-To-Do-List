# To Do - Many to Many

#### A to do list with categories and tasks

#### By Abigail Rolling, Ryan King, and Austin Minnon

## Description

This to do app organizes tasks by category and completion state; each task can belong to multiple categories. It will also filter tasks by whether they belong to all or each of multiple categories.

## Setup/Installation Requirements

Clone this repository:
```
$ cd ~/Desktop
$ git clone github address
$ cd folder-name
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `to_do` database:
```
$ psql
$ CREATE DATABASE to_do;
$ psql to_do < to_do.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```
## Known Bugs

- Does not sort results alphabetically or consistently.
- Tasks that share categories not sorting correctly

## Technologies Used

* Java
* junit
* Gradle
* Spark
* fluentlenium
* PostgreSQL
* Bootstrap

### License

Licensed under the GPL.

Copyright (c) 2016 **Abigail Rolling, Ryan King, and Austin Minnon**
