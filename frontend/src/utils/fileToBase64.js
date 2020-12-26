export function toBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result.split(";base64,")[1]);
    reader.onerror = (error) => reject(error);
  });
}
