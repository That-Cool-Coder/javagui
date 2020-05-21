# README for javagui

#### This is a test program designed to test my theories of Java GUI's. It is the next step from CovidSim, which built understanding of Java class interaction. This will also test accounts, although they won't be secure (no hashing). The fact that they are stored in .dat files makes them harder to read, if anything.

#### This program is designed to hold brief thoughts and other notes that the users make.

## Basic class breakdown
#### All of the GUI elements are controlled by class Main (the only static class)
#### The consts are controlled by class Main also
#### Enums etc are stored in the top of Main.java
#### The database is controlled by class Database
#### To facilitate multiple returns of result and status, there are  a group of classes such as ResultAndStatus, UserAndStatus and BoolAndStatus. These contain a string a user obj and a boolean respectively. In short they package two data types into one object

## User account storage
#### Users are stored as User objects in an arrayList. This arrayList is saved and read to the file (as of writing) data/users.dat
#### They are written and read using the java read/write things, and are not stored in a utf-8 format, which limits growth potential as programs in other languages won't be able to read it

## Info storage
#### In this program, this folder is called info. It's a bit of a useless/bad/misleading name but it is better than docu or notes. Basically it's the folder in which all of the users' saved data is stored
#### This shares similar problems to the user file in terms of growth potential
#### The notes are stored as objects of class Note. Each note contains the username of the user who created it so that they are private
#### Each note has its own .dat file