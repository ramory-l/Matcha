// import moment from "moment";

export function shallowEqual(object1, object2) {
  const keys1 = Object.keys(object1);
  const keys2 = Object.keys(object2);

  if (keys1.length !== keys2.length) {
    return false;
  }

  for (let key of keys1) {
    if (object1[key] !== object2[key]) {
      return false;
    }
  }

  return true;
}

export function deepEqual(object1, object2) {
  const keys1 = Object.keys(object1);
  const keys2 = Object.keys(object2);

  if (keys1.length !== keys2.length) {
    return false;
  }

  for (const key of keys1) {
    const val1 = object1[key];
    const val2 = object2[key];
    const areObjects = isObject(val1) && isObject(val2);
    if (
      (areObjects && !deepEqual(val1, val2)) ||
      (!areObjects && val1 !== val2)
    ) {
      return false;
    }
  }

  return true;
}

function isObject(object) {
  return object != null && typeof object === "object";
}

export function findSimilarityInForms(userForm, anotherUser) {
  // console.log(Number(moment(anotherUser.birthday).fromNow(true).slice(0, 3)));
  const likes = userForm.likesDislikes.likes;
  const dislikes = userForm.likesDislikes.dislikes;
  const blackList = userForm.blackList;

  if (blackList.filter((blocked) => blocked.id === anotherUser.id).length)
    return false;

  if (
    likes.filter((like) => like.id === anotherUser.id).length ||
    dislikes.filter((dislikes) => dislikes.id === anotherUser.id).length
  )
    return false;

  if (userForm.id === anotherUser.form.id) return false;

  const userFormKeys = Object.keys(userForm);

  let neededGender = [];
  if (userForm.man) neededGender.push("man");
  if (userForm.woman) neededGender.push("woman");

  if (neededGender.length === 0) return false;

  if (neededGender.includes(anotherUser.gender)) {
    for (const key of userFormKeys) {
      if (userForm[key] === true && anotherUser.form[key] === true) {
        return true;
      }
    }
  }
  return false;
}
