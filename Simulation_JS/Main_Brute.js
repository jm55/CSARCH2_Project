import {Checker} from './Checker.js'
import {Unicode} from './Unicode.js'

//TEST AREA
let c = new Checker();
let u = new Unicode();

//LONG TEST
var start = ["0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"];
var end = ["1","A","32","64","1F4","3E8","1388","2710","C350","186A0","7A120","F4240","4C4B40","989680","E4E1C0","10FFFFF"];
var time = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];

//SHORT TEST
//var time = [0,0,0,0,0,0];
//var start = ["0","0","0","0","0","0"];
//var end = ["1","A","32","64","1F4","3E8"];

var runs = 5;
console.log("start: " + start);
console.log("end: " + end);
for(var i = 1; i <= runs; i++){
    for(var j = 0; j < start.length; j++){
        var startDate = new Date();
        for(var k = parseInt(start[j], 16); k <= parseInt(end[j],16); k++)
            u.SetUnicode(k.toString(16));
        var endDate   = new Date();
        console.log(((endDate - startDate) / 1000) + "s");
        time[j] = time[j] + ((endDate - startDate) / 1000);
    }
}
for(var j = 0; j < start.length; j++)
        time[j] = time[j] / runs;
    console.log(time);
