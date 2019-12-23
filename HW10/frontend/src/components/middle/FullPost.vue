<template>
    <div class="full-post">
        <Post :post="this.post"/>
        <div class="add-comment">
            <div class="header">Add a comment</div>
            <form @submit.prevent="onAdd">
                <label for="text">
                    Text:
                </label>
                <textarea id="text" rows="20" v-model="text"/>
                <div class="error">{{error}}</div>
                <div>
                    <input type="submit" value="Add"/>
                </div>
            </form>
        </div>
        <div class="comment" v-for="comment in post.comments" :key="comment.id">
            {{comment.text}}
            <div class="information">
                By {{comment.user.login}}
            </div>
        </div>
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        data: function () {
            return {
                text: "",
                error: ""
            }
        },
        name: "FullPost",
        props: ['post'],
        components: {Post},
        methods: {
            onAdd: function () {
                this.$root.$emit("onAddComment", this.post, this.text);
            }
        },
        beforeCreate() {
            this.$root.$on("onAddCommentSuccess", () => {
                this.text = this.error = "";
            })
        },
        beforeMount() {
            this.text = this.error = "";
            this.$root.$on("onAddCommentValidationError", error => this.error = error);
        }
    }
</script>

<style scoped>
    label {
        display: block;
        margin-top: 1rem;
    }

    textarea {
        width: 60%;
        box-sizing: border-box;
    }

    input[type='submit'] {
        margin-top: 1rem;
        width: 6rem;
    }

    .error {
        color: var(--error-color);
    }
</style>