//import * as Utils from 'app.js';
import {Checker} from './Checker.js';
import {Unicode} from './Unicode.js'

//TEST AREA
let c = new Checker();
let u = new Unicode();
var list = ["245D6","1CAFE","42069","Youtube","Meta","ABCDEF","10FFFFF",
        "1FFFFF","","U+ABCDEF","U+3041","U+3086","308C","3044","0x306A"];
console.log("Input", "UTF8", "UTF16", "UTF32");
for(var i = 0; i < list.length; i++){
if(c.CheckInput(list[i]) != null){
    u.SetUnicode(c.CheckInput(list[i]));
    console.log(list[i] + ", " + u.GetUTF8 + ", " + u.GetUTF16 + ", " + u.GetUTF32 + ", " + u.GetChar);
}
else
    console.log("Invalid input: " + list[i]);
}