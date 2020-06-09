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
const nameOfForm = "contact";
const nameOfContact = "Name";
const emailOfContact = "Email";
const numberOfContact = "Number";
const deleteButton = "Delete";
const idOfContactList = "contact-list";

let positions = [ 'Freelance Web Developer', 'Robotics Builder', 'Graphic Designer', 'Entrepreneur' ];
let i = 0;
let contactIds = [];

setInterval(changePositionDisplayed, 3000);

function  changePositionDisplayed(){
    $('#position').fadeTo(300, 0).fadeTo(300, 1); 
    $('#position').text(positions[i]);
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

function getGreeting() {
  fetch('/data')
  .then(response => response.text())
  .then((name) => {
    document.getElementById('greeting').innerText = name;
  });
}

function getJSONData(){
  fetch('/data')  
  .then(response => response.json()) 
  .then((message) => {

      let messageConntainer = document.getElementById("messages");

      for(let i = 0; i < message.length; i++){
        let pTag = document.createElement('p');
        let text = document.createTextNode(message[i]);
        pTag.appendChild(text);
        messageConntainer.appendChild(pTag);
      }

  });
}

function getComment(){
  fetch('/comment')  
  .then(response => response.json()) 
  .then((comments) => {
    checkNumberOfComments(comments);
    let commentContainer = document.getElementById("comments");

    comments.forEach((comment) => {
      commentContainer.appendChild(createListElement(comment));
    });
  });
}
function checkNumberOfComments(array){
  let commentsMax = 5;  // This is an arbitrary number because I do no want that many comments on my page
  if(array.length > commentsMax ){
    array.clear();
  }
}
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function loadContacts(){
  fetch('/list-contacts')
  .then(response => response.json())
  .then((contacts) => {
    const taskListElement = document.getElementById(idOfContactList);
    console.log(contacts);
    contacts.forEach((contact) => {
      taskListElement.appendChild(createContactElement(contact));
    });
  });
}

function createContactElement(contact) {
  contactIds.push(contact.id);

  const contactElement = document.createElement('li');
  contactElement.className = nameOfForm;

  const nameElement = document.createElement('span');
  nameElement.innerText = nameOfContact + ": " + contact.name;

  const emailElement = document.createElement('span');
  emailElement.innerText = emailOfContact + ": " + contact.email;

  const numberElement = document.createElement('span');
  numberElement.innerText = numberOfContact + ": " + contact.number;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = deleteButton;
  deleteButtonElement.addEventListener('click', () => {
    deleteTask(contact);
    contactElement.remove();
  });
  contactElement.appendChild(nameElement);
  contactElement.appendChild(emailElement);
  contactElement.appendChild(numberElement);
  contactElement.appendChild(deleteButtonElement);
  
  return contactElement;
}

function deleteTask(contact) {
  const params = new URLSearchParams();
  params.append('id', contact.id);
  fetch('/delete-contact', {
        method: 'POST', 
        body: params
        }
      );
}

function deleteAll() {
  contactIds.forEach((contact) => {
    const params = new URLSearchParams();
    params.append('id', contact);
    fetch('/delete-contact', {
          method: 'POST', 
          body: params
          }
        );
  })
  location.reload();
}