import React, { useEffect, useRef, useState } from "react";
import ReactTags from "react-tag-autocomplete";
import { getCurrentUser } from "../services/authService";
import { createTag, deleteTag, getTopTags } from "../services/tagsService";
import { getUserTags } from "../services/userService";
import "./styles/tagsInput.scss";

const TagsInput = ({ userId, editMode, isSearchForm, onHandleTags }) => {
  const [tags, setTags] = useState([]);
  const [suggestions, setSuggestions] = useState([]);
  const tagInput = useRef(null);

  useEffect(() => {
    const fetchTags = async () => {
      const { data: tags } = isSearchForm
        ? await getUserTags(getCurrentUser().id)
        : await getUserTags(userId);
      const { data: suggestions } = await getTopTags();
      if (isSearchForm) {
        const tagsStr = tags
          .map((tag) => {
            return `${tag.name}`;
          })
          .join(",");
        onHandleTags(tagsStr);
      }
      setTags(tags);
      setSuggestions(suggestions);
    };
    fetchTags();
  }, [userId, isSearchForm, onHandleTags]);

  const handleDelete = async (i) => {
    const newTags = tags.slice(0);
    const tag = newTags.splice(i, 1);
    if (!tag[0]) return;
    await deleteTag(tag[0].name);
    if (isSearchForm) {
      const tagsStr = newTags
        .map((tag) => {
          return `${tag.name}`;
        })
        .join(",");
      onHandleTags(tagsStr);
    }
    setTags(newTags);
  };

  const handleAddition = async (tag) => {
    const newTags = [].concat(tags, tag);
    await createTag(tag.name);
    if (isSearchForm) {
      const tagsStr = newTags
        .map((tag) => {
          return `${tag.name}`;
        })
        .join(",");
      onHandleTags(tagsStr);
    }
    setTags(newTags);
  };

  if (!editMode && !isSearchForm) {
    return (
      <>
        <label>Tags:</label>
        {tags.length === 0 ? (
          <p>No tags yet.</p>
        ) : (
          tags.map((tag) => (
            <a
              href={`/?tag=${tag.name}`}
              key={tag.id}
              className={`badge badge-info mx-1`}
            >
              {`#${tag.name}`}
            </a>
          ))
        )}
      </>
    );
  }

  return (
    <ReactTags
      ref={tagInput}
      tags={tags}
      suggestions={suggestions}
      onDelete={handleDelete}
      onAddition={handleAddition}
      autoresize={false}
      allowNew={true}
    />
  );
};

export default TagsInput;
