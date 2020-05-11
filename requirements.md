# Project Requirements

Alexander Graham (UP812259)

- [Project Requirements](#project-requirements)
  - [1. Introduction](#1-introduction)
    - [1.1 Aim](#11-aim)
    - [1.2 Key Personnel](#12-key-personnel)
    - [1.3 Future Proofing](#13-future-proofing)
    - [1.4 Rationale for development](#14-rationale-for-development)
    - [1.5  Interactions and dependencies](#15-interactions-and-dependencies)
    - [1.6  Timetable](#16-timetable)
  - [2.0 Data Requirements](#20-data-requirements)
    - [2.1 Externally Sourced Data](#21-externally-sourced-data)
    - [2.2 Internally Sourced Data](#22-internally-sourced-data)
    - [2.3 General Requirements](#23-general-requirements)
  - [3.0 Document Control](#30-document-control)
    - [3.1 History](#31-history)

## 1. Introduction

### 1.1 Aim

To create a web based system which allows for a community of users to have a shared diary and contacts system.

### 1.2 Key Personnel

| Role              | Person(s)        |
| ----------------- | ---------------- |
| Responsible owner | Alexander Graham |
| Design Authority  | Jim Briggs       |
| Development Team  | Alexander Graham |

### 1.3 Future Proofing

The system will be designed in such a way that its architecture will allow for easy future improvements and updates. This will allow for new features if and when required and the system to adapt with the users.

### 1.4 Rationale for development

The system is designed to meet the requirements of the client.

### 1.5  Interactions and dependencies

This system is designed to be run as 'standalone'. It can function independently of other systems and will require it's own login mechanism.

### 1.6  Timetable

| Release | Code  |       Date |
| ------- | :---: | ---------: |
| Alpha   |   A   | 2020-04-21 |
| Main    |  M1   | 2020-05-04 |

D (desirable) requirements will be assigned to a particular release as appropriate.

## 2.0 Data Requirements

### 2.1 Externally Sourced Data

<table>
  <tbody>
    <tr>
      <th>Requirements</th>
      <th>Type</th>
      <th>Status</th>
    </tr>
    <tr>
      <td>
        <ol>
        <li> User data that should be held by the system includes:
        <ol>
          <li>Username
          <li>Password
          <li>Name
          <li>Address
          <li>Phone Number
          <li>Email
        </ol>
        </li>
        </ol>
      </td>
      <td align="center">A</td>
      <td align="right">Not Started</td>
    </tr>
    <tr>
      <td>
        <ol start='2'>
        <li> Diary appointment data that should be held includes:
        <ol>
          <li>Appointment Start Date/Time
          <li>Appointment End Date/Time
          <li>Appointment Name
          <li>Appointment Description
          <li>Appointment Owner
          <li>Appointment Attendees
        </ol>
        </li>
        </ol>
      </td>
      <td align="center">A</td>
      <td align="right">Not Started</td>
    </tr>
  </tbody>
</table>

### 2.2 Internally Sourced Data

<table>
  <tbody>
    <tr>
      <th>Requirements</th>
      <th>Type</th>
      <th>Status</th>
    </tr>
    <tr>
      <td>
        <ol>
        <li> A list of all system contacts
        </ol>
      </td>
      <td>A</td>
      <td>Not Started</td>
    </tr>
    <tr>
      <td>
        <ol start="2">
        <li> A list of all appointments for a given user (defaults to current user)
        </ol>
      </td>
      <td>A</td>
      <td>Not Started</td>
    </tr>
    <tr>
      <td>
        <ol start="3">
        <li> A list of all appointments for a given day
        </ol>
      </td>
      <td>M1</td>
      <td>Not Started</td>
    </tr>
  </tbody>
</table>

### 2.3 General Requirements

| Requirement                                                                                                                   | Type | Status      |
| ----------------------------------------------------------------------------------------------------------------------------- | ---- | ----------- |
| 1. A user must be able to create a new account holding the data listed in 2.1.1.                                              | A    | Not Started |
| 2. Data added to the database must be validated. Invalid data must be flagged and the user allowed to correct it.             | M1   | Not Started |
| 3. Once a user is created only that user can update their details.                                                            | M1   | Not Started |
| 4. Logged in (and only logged in) users can browse all users' records.                                                        | A    | Not Started |
| 5. No diary appointments can have attendees clashing with other events, give the user the ability to remove people who clash. | M1   | Not Started |
| 6. An appointment owner can cancel events and this will free all attendants for other events.                                 | A    | Not started |
| 7. Calendar View: The users should be able to browse calendar appointments as a calendar 'grid' view                          | D    | Not Started |

## 3.0 Document Control

### 3.1 History

| Version | Date       | Changer     | Change                   |
| ------- | ---------- | ----------- | ------------------------ |
| 1.1     | 2020-02-16 | Alex Graham | Added draft requirements |
| 1.0     | 2020-02-11 | Alex Graham | Initial Document         |
