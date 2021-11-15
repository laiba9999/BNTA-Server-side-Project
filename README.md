# BNTA - Server-Side Project (Moop City)

The Server Side Project is a seven-day team project undertaken during the BNTA Bootcamp where we had to create a backend application with an MVP of allowing users to make GET and Post requests. 
<br/> 
Our application utilises the tech stacks Java, Spring and PostgreSQL to enable users to store information using CRUD operations. We had dependencies such as Flyway for version control of our database and JDBC API to connect and access the database. We also utilised the Hikari data source as the connection pool mechanism because it is lightweight and better performing.

## Table Of Contents

- [Planning](#Planning)
- [Challenges we faced](#Challenges-we-faced)
- [Testing](#Testing)
- [What we learnt](#What-we-learnt)
- [Final Presentation](#Final-Presentation)
- [Improvements for the future](#Improvements-for-the-future)
- [How to install](#How-to-install)
- [How to setup](#How-to-setup)
- [How to use](#How-to-use)
- [Credits to](#Credits-to)

## Planning
Before diving into the coding, we spent the first day on planning. 

### Kanban Board
We handled time-management by creating a Kanban Board using Trello which helped us create and assign tasks to each member. We assigned different tasks to allow for individual, in-pair and group work.
![Image of the kanban board on trello](/documentation_images/Trello-Kanban-Board.png)

### Relational Database Model
We created models for our relational database to help structure the information and data into a set of tables. We created links between each table to determine the different relationships such as one-to-one, one-to-many, etc. This helped us visualise our database before creating it.
![Image of the relational database model](/documentation_images/Miro-Model-Database-Table.png)

### Classes Model
We had a discussion about the possible classes that we would need including their fields, data type, and methods. Then modelled it using the website Miro.

**Citizen Classes**
![Image of the classes model](/documentation_images/Miro-Citizen-Class-Model.png)

**Allotment Classes**
![Image of the classes model](/documentation_images/Miro-Allotment-Class-Model.png)

**House Classes**
![Image of the classes model](/documentation_images/Miro-House-Class-Model.png)

**Workplace Classes**
![Image of the classes model](/documentation_images/Miro-Workplace-Class-Model.png)

Once this was all planned out and agreed upon, we created this repository to allow us to start programming.

## Challenges we faced
During the project, we came across a few challenges. Some challenges were harder to solve than others but the most memorable ones were:

1. Creating mock data and using Flyway to track our database.
 - This was an issue we came across when we were trying to create mock data to test our code. As this was the first challenge we came across that took us more than an hour to fix, it was one of the most memorable since we worked as a team to solved this using both the documentation and StackOverflow.

2. Connection point issues between the classes.
 - This issue came about because we forgot to remove the abstract keyword from a class. This caused a few headaches as when we were trying to debug, we looked at our code multiple times to check if we added the necessary Java Bean annotations to the classes. After awhile, we were able to find the issue and solve it quickly. This is memorable to us because it was a small issue that had big repercussions.

## Testing
We tested our code using the frameworks JUnit, AssertJ, and Mockito.
<br/>
Since we tested our code as we were writing them, we were able to reduce the amount of potential errors that might have occured as our application became more complicated.
<br/>
From this we were able to save a lot of time and attempt our challenge implementation.

## What we learnt
Overall, this project was a fun and exciting experience. We were able to solidify the content taught in the Bootcamp by creating an API that users could interact with. 
<br/>
Through this project, we were able to learn how to use Git effectively to work collaboratively in a group and create CRUD operations from scratch. We also practiced writing tests to test our application using the frameworks JUnit, AssertJ and Mokito.
<br/>
During the project, we became more adaptable by utilising each member's strengths and learnt how to write tests effectively to reduce errors from happening later on.

## Final Presentation
We presented our project on Thursday 18th November 2021 at 15:00pm to our colleagues at the bootcamp, the instructors and potential clients.
<br/>
The presentation included a description of the application, the reasons behind our choice of topic, the planning stages, a demo of the application, code that we were proud of, and a short Q and A section where we answered questions from the audience.
<br/>
### Slides
Here are the slides from the presentation.
![Slide 1](/documentation_images/slide-1.png)
![Slide 2](/documentation_images/slide-2.png)
![Slide 3](/documentation_images/slide-3.png)
![Slide 4](/documentation_images/slide-4.png)
![Slide 5](/documentation_images/slide-5.png)
![Slide 6](/documentation_images/slide-6.png)
![Slide 7](/documentation_images/slide-7.png)
![Slide 8](/documentation_images/slide-8.png)
![Slide 9](/documentation_images/slide-9.png)
![Slide 10](/documentation_images/slide-10.png)
![Slide 11](/documentation_images/slide-11.png)

## Improvements for the future
Rather than starting by building a database that we knew we could build and then wondering what the user experience would be like afterwards it might've been a smoother workflow to start with the user experience first, then work our way backwards to the backend.
<br/>
This might've allowed us to avoid the pivot and ultimately save time in our design process.
<br/>
Another improvement we would've liked to add was to collate citizens into families. So we could retrieve data on an entire family or find the house id of a specific family.

## How to install

  - To install the backend application, press the green `Code` button in this GitHub repository and select the option <br/> `Download Zip`. 
  - Once downloaded, use an unzipper application to unzip the files.

## How to setup

### Setup
Software you will need:
  - Intelij IDE
  - PostgreSQL
  - Java SE17

1. Open up Intellij and open the directory with the files that were downloaded in the install section using the `Open` button in Intellij.
2. Use the PostgreSQL application to initialise a server by pressing the `Initialize` button and start up the sever by pressing the `Start` button.
3. Open up a new terminal and use the command `psql` to start up the sever connection then type the command `CREATE DATABASE severside` to create a database called serverside. (Note: This database is needed to run the application)
4. Go back on Intellij and press the green build button (looks like a hammer) to build and run the application.
<br/>
Note: A more detailed version of how to set up will be on the Wiki of this repository.

## How to use

Please refer to the Wiki section of this repository to learn how to use the application.

## Credits to

- Vinh (Me) [Github](https://github.com/vinhchugg)
- Yacine (Nino) [Github](https://github.com/mechanin)
- Jonathan [Github](https://github.com/Djontleman)
- Laiba [Github](https://github.com/laiba9999)
