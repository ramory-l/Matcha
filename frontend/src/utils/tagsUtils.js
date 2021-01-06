export function tagsToArray(tags) {
  const tagsArray = tags
    ? tags.split(",").map((tag, index) => {
        return {
          id: index,
          name: tag,
        };
      })
    : [];
  return tagsArray;
}

export function tagsToString(tags) {
  const tagsString = tags
    .map((tag) => {
      return `${tag.name}`;
    })
    .join(",");
  return tagsString;
}
