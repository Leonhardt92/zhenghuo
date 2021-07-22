const baseUrl = api.baseUrl;

function getFaceCount(templateName) {
  return fetch(`${baseUrl}/gif/getFaceCount/${templateName}`).then(res => res.text())
}
