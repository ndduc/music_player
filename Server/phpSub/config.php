<?php
$CFG->dbtype    = 'sqlsrv';         // Required
$CFG->dbhost    = '192.168.1.5:1433;instance=MGRLLIC_02\\Duc';      // Assuming that MSSQL is on the same server, otherwise 
                                    // use the actual name or IP address of your database server
$CFG->dbname    = 'POSBDat';         // The name of the newly created Moodle database
$CFG->dbuser    = 'chandler';   // Usually the 'sa' account (dbowner perms are enough)
$CFG->dbpass    = 'LeeLee127912';
$CFG->dbpersist =  true;
$CFG->prefix    = 'mdl_';           // The prefix can be changed per individual preferences, 
                                    // but NEVER leave this blank!
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

