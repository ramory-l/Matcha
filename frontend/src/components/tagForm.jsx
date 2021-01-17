import React from "react";
import Form from "./common/form";
import Joi from "joi";
import { createTag, deleteTag } from "../services/tagsService";
import { getUserTags } from "../services/userService";
import "./styles/tagInput.scss";

class TagForm extends Form {
  state = {
    data: {
      tags: [],
      tagName: "",
    },
    errors: {},
  };

  async componentDidMount() {
    const { data: tags } = await getUserTags(this.props.userId);
    this.setState({ data: { tags, tagName: "" } });
  }

  async componentDidUpdate(prevProps) {
    if (this.props.userId !== prevProps.userId) {
      const { data: tags } = await getUserTags(this.props.userId);
      this.setState({ data: { tags, tagName: "" } });
    }
  }

  schema = Joi.object({
    tags: Joi.array().optional(),
    tagName: Joi.string().alphanum().min(2).max(16).required(),
  });

  handleTagClick = async (e, editMode) => {
    if (editMode) {
      e.preventDefault();
      const tagName = e.target.innerText;
      const tags = [...this.state.data.tags];
      await deleteTag(tagName);
      const filteredTags = tags.filter((tag) => tag.tag !== tagName);
      this.setState({ data: { tags: filteredTags, tagName: "" } });
    }
  };

  doSubmit = async () => {
    const tags = [...this.state.data.tags];
    const { tagName } = this.state.data;
    await createTag(tagName);

    const lastTagId = tags.length !== 0 ? tags[tags.length - 1].id + 1 : 0;
    const newTag = { id: lastTagId, tag: tagName };
    tags.push(newTag);
    this.setState({ data: { tags, tagName: "" } });
  };

  render() {
    const { tags } = this.state.data;
    const { editMode } = this.props;
    const tagClass = editMode ? "danger" : "info";

    return (
      <div className="form-group">
        <label>Tags:</label>
        {tags.length === 0 ? (
          <p>No tags yet.</p>
        ) : (
          tags?.map((tag) => (
            <a
              href={`/?tag=${tag.tag}`}
              key={tag.id}
              onClick={(e) => this.handleTagClick(e, editMode)}
              className={`badge badge-${tagClass} mx-1`}
            >
              {`#${tag.tag}`}
            </a>
          ))
        )}
        {editMode
          ? this.renderInputWithButton(
              "tagName",
              "Tag name",
              "Add Tag",
              this.doSubmit
            )
          : null}
      </div>
    );
  }
}

export default TagForm;
