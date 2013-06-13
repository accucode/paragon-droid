=====================================================================
== README - SQL PATCHES
=====================================================================

This folder contains ad hoc sql scripts used in database migrations.

A file can contain multiple sql statements, separated by semicolons.
Note that semicolons are only used as statement delimiters if they 
appear at the end of a line.  Otherwise, they are assumed to be an
sql literal (e.g.: part of a string constant).

Sql is executed using SQLiteDatabase.execSQL(...).
    Execute a single SQL statement that is NOT a SELECT or any other 
    SQL statement that returns data.
So... NO select statements.

Comments can be added by using a pound sign.  Any line that starts 
with a pound sign (#) is assumed to the comment.  The pound must be
at the very beginning of the line (column 1).

Blank lines are ignored.

=====================================================================
== Sample
=====================================================================

# This is a comment.
# Remember that blank lines are simply ignored.

# An example of a simple update statement, split across multiple lines
# and terminated by a semicolon...
update item 
    set active = 1 
    where active = 0;

# Just another statement...
update item
    set phone = null
    where shouldReset = 1;

=====================================================================
== Type affinities
=====================================================================

http://www.sqlite.org/datatype3.html 

Sqlite does not have strict column types.  However it does use
loose column affinities and we generally apply them as follows:

    text    // used for all string values, no specific length
    integer // used for integer values, max ~2^63
    real    // used for floating point, 
    none    // possibly used for blob data.
    numeric // generally not used

=====================================================================
== Collating sequence
=====================================================================

http://www.sqlite.org/datatype3.html#collation

Sqlite defaults to a binary collating sequence for text data.
This means that strings are case-sensitive by default.  Since
this is generally NOT what we want, we specify an alternate
collating sequence (nocase) when the table is defined.

=====================================================================
== Data types
=====================================================================

String
    Stored as "text".
    Requires an explicit "nocase" collating sequence.

Integer
    Stored as "integer".

Double
    Stored as "real".
    
Boolean
    Stored as "integer".
    true=1, false=0.

Timestamp
    Stored as "integer"
    Milliseconds since Jan 1, 1970 (may be negative).
    We currently don't support years before 1800.

Date
    Stored as "integer".
    Milliseconds since Jan 1, 1970 (may be negative).
    The value is compatible with Timestamp and is effectively 
    stored as midnight of each day.

Money
    Stored as "integer".
    Fixed precision data such as money should be stored as integers
    to avoid rounding errors.  Since sqlite support 64-bit integer
    this allows us to effectively store +- 1 quadrillion with two  
    places implicitly to the right of the decimal.  So, money columns
    are actually stored as cents, then converted to decimal money
    values in the app.  
    
=====================================================================
== Examples (create table)
=====================================================================

# create a temp table with an auto-incrementing key. 
create table temp
(
    id integer primary key autoincrement not null,
    name text collate nocase
);


# create a person table with uid. 
create table temp
(
    uid text primary key not null,
    name text collate nocase
);


# use the <if not exists> clause.
create table if not exists person
(
    uid text primary key not null,
    name text collate nocase
);

=====================================================================
== Examples (alter table)
=====================================================================

# add a column 
alter table person add column phone text collate nocase;

# rename table 
alter table person rename to person2;

=====================================================================
== Examples (update)
=====================================================================

update person
    set name = 'bob'
    where active = 1;

=====================================================================
== Examples (basics)
=====================================================================

# show tables
select * from sqlite_master where type = 'table' order by tbl_name;

# show indexes
select * from sqlite_master where type = 'index' order by tbl_name;


=====================================================================
== end.
=====================================================================
