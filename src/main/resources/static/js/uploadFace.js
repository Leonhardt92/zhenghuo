async function initUploadFace(templateName) {
  const faceNumber = Number(await getFaceCount(templateName));
  const $faceInput = $('#template-face-input')
    .clone()
    .removeClass('hidden')
    .removeAttr('id');
  const $faceInputContainer = $('#face-input-container').empty();
  new Array(faceNumber).fill(0).forEach(() => {
    $faceInputContainer.append(
      $faceInput.clone().attr('data-template', templateName)
    );
  })
}
