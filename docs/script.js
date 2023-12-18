const imgElement = document.getElementById('randomImage');
const nextImage = new Image();

window.onload = function() {
  imgElement.src = imgs[0];
  loadNextImage();
  document.body.addEventListener('click', changeImage);
};

function loadNextImage() {
  const randomIndex = Math.floor(Math.random() * imgs.length);
  nextImage.src = imgs[randomIndex];
}

function changeImage() {
  imgElement.src = nextImage.src;
  loadNextImage();
}
