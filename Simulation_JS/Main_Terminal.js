import {Checker} from './Checker.js'
import {Unicode} from './Unicode.js'

//TEST AREA
let c = new Checker();
let u = new Unicode();
var list = ["245D6","1CAFE","42069","Youtube","Meta","ABCDEF","10FFFFF",
            "1FFFFF","","U+ABCDEF","U+3041","U+0x3086","0xU+308C","3044","0x306A","zxcvbnm","0x10FF FF",
            "0x10FFF","10FF", "u+10F", "U+10","0x","U+0x","U+","U+AB","0xAB","FFFF","10","10F","10FF",
            "10FFF","10FFFF","10FFFFF"];
console.log("Input, UTF8, UTF16, UTF32, Character");
for(var i = 0; i < list.length; i++){
    if(c.CheckInput(list[i]) != null){
        u.SetUnicode(c.CheckInput(list[i]));
        console.log(list[i] + ": ", u.GetAll);
        //console.log(list[i] + ": ", u.GetFormatted);
        //console.log(list[i] + ", " + u.GetUTF8 + ", " + u.GetUTF16 + ", " + u.GetUTF32 + ", " + u.GetChar);
    }
    else{
        if(list[i].length === 0)
            console.log("Invalid input: Empty input");
        else
            console.log("Invalid input: " + list[i]);
    }
}