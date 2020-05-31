// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */

let positions = [ 'Freelance Web Developer', 'Robotics Builder', 'Graphic Designer', 'Entrepreneur' ];
let i = 0;

setInterval(changePositionDisplayed, 3000);


function  changePositionDisplayed(){
	$('#position').fadeTo(300, 0).fadeTo(300, 1); 
    $('#position').text(titles[i]);
    
    i++;
    if (i === positions.length) {
      i = 0;
    }
}



function playGame(){
  window.open('https://abakirh.github.io/pokebattle/index.html', '_blank');
}

function viewGame(){
  window.open('https://github.com/AbakirH/AbakirH.github.io/blob/master/pokebattle/sketch.js', '_blank');
}