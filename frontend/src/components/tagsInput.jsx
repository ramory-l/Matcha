import React, { useEffect, useRef, useState } from "react";
import ReactTags from "react-tag-autocomplete";
import { getUserForm } from "../services/formService";
import { createTag, deleteTag, getTopTags } from "../services/tagsService";
import { getUserTags } from "../services/userService";
import "./styles/tagsInput.scss";

const TagsInput = ({ userId, editMode, isSearchForm, onTagsForSearchForm }) => {
  const [tags, setTags] = useState([]);
  const [suggestions, setSuggestions] = useState([]);
  const tagInput = useRef(null);

  useEffect(() => {
    const fetchTags = async () => {
      let tags;
      if (isSearchForm) {
        tags = getUserForm().tags;
      } else {
        const { data } = await getUserTags(userId);
        tags = data;
      }
      const { data: suggestions } = await getTopTags();
      setTags(tags);
      setSuggestions(suggestions);
    };
    fetchTags();
  }, [userId, isSearchForm]);

  const handleDelete = async (i) => {
    const newTags = tags.slice(0);
    const tag = newTags.splice(i, 1);
    if (!tag[0]) return;
    if (!isSearchForm) await deleteTag(tag[0].name);
    else {
      onTagsForSearchForm(newTags);
    }
    setTags(newTags);
  };

  const handleAddition = async (tag) => {
    const newTags = [].concat(tags, tag);
    if (!isSearchForm) await createTag(tag.name);
    else {
      onTagsForSearchForm(newTags);
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
