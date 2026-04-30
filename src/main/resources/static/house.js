       
function message(event) {
  const fields = document.querySelectorAll("#Name, #CreatePassword,#ConfirmPassword,#Email, #City, #States");
  let emptyFound = false;
 const nam = document.getElementById("Name").value;
   const headerElement = document.querySelector("h1.head");

if (headerElement) {
    headerElement.textContent = nam ? `Welcome, ${nam}` : 'Welcome, Guest!';
}    
 if(nam.length<=12){
localStorage.setItem("Name", nam); // Save permanently
 }
 else{
  alert("enter the name less than 13 characteres");
  event.preventDefault();
    return false;
 }

 const pass = document.querySelector('#CreatePassword').value;
const isWeak = !/[!@#$%^&*(),.?":{}|<>]/.test(pass);

if (pass.length < 6 || isWeak) {
  alert("Create a strong password with at least 6 characters and one special character.");
  event.preventDefault();
  return false;
}
}
function clear() {
  document.querySelectorAll("input, select").forEach(el => el.value = "");
}