// Function that moves the picture at random places on the screen every 0.5 seconds
function movePicture() {
for (let i = 0; i < 2; i++) {
    let img = document.getElementById(`gifDrole${i}`);
    // Get the width and height of the screen
    let width = window.innerWidth;
    let height = window.innerHeight;

    let x = Math.floor(Math.random() * width-400);
    let y = Math.floor(Math.random() * height-236);

    img.style.position = 'absolute';
    img.style.left = x + 'px';
    img.style.top = y + 'px';
}

setTimeout(movePicture, 200);
}

window.onload = movePicture;