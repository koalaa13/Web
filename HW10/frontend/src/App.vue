<template>
    <!--suppress HtmlUnknownTag -->
    <body id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users"/>
    <Footer/>
    </body>
</template>

<script>
    import Header from './components/Header'
    import Middle from './components/Middle'
    import Footer from './components/Footer'
    import axios from 'axios'

    export default {
        name: 'app',
        data: function () {
            return {
                user: null,
                posts: [],
                users: []
            }
        },
        components: {
            Header,
            Middle,
            Footer
        }, beforeCreate() {
            axios.get("/api/1/users").then(users => this.users = users["data"]);
            axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);

            this.$root.$on("onLogout", () => {
                localStorage.removeItem("jwt");
                this.user = null;
            });

            this.$root.$on("onJwt", (jwt, enter) => {
                axios.defaults.headers = {
                    Authorization: "Bearer " + jwt
                };

                axios.get("/api/1/users/authorized").then(response => {
                    this.user = response.data;
                    if (enter) {
                        this.$root.$emit("onEnterSuccess");
                    }
                });
            });

            this.$root.$on("onRegister", (login, name, password) => {
                axios.post("/api/1/users", {
                    login: login,
                    password: password,
                    name: name
                }).then(response => {
                    localStorage.setItem("jwt", response.data);
                    this.$root.$emit("onJwt", response.data, true);
                    axios.get("/api/1/users").then(users => this.users = users["data"]);
                }).catch(error => {
                    this.$root.$emit("onRegisterValidationError", error.response.data);
                });
            });

            this.$root.$on("onAddPost", (title, text) => {
                axios.post("/api/1/posts", {
                    title: title,
                    text: text,
                    user: this.user
                }).then(() => {
                    this.$root.$emit("onAddPostSuccess");
                    axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);
                    axios.get("/api/1/users").then(users => this.users = users["data"]);
                }).catch(error => {
                    this.$root.$emit("onAddPostValidationError", error.response.data);
                });
            });

            this.$root.$on("onAddComment", (post, text) => {
                axios.post("/api/1/posts/addComment/" + post.id, {
                    text: text,
                    user: this.user
                }).then(() => {
                    this.$root.$emit("onAddCommentSuccess");
                    axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);
                    axios.get("/api/1/users").then(users => this.users = users["data"]);
                }).catch(error => {
                    this.$root.$emit("onAddCommentValidationError", error.response.data);
                });
            });

            this.$root.$on("onEnter", (login, password) => {
                axios.post("/api/1/jwt", {
                    login: login,
                    password: password
                }).then(response => {
                    localStorage.setItem("jwt", response.data);
                    this.$root.$emit("onJwt", response.data, true);
                }).catch(error => {
                    this.$root.$emit("onEnterValidationError", error.response.data);
                });
            });
        }, beforeMount() {
            if (localStorage.getItem("jwt") && !this.user) {
                this.$root.$emit("onJwt", localStorage.getItem("jwt"), true);
            }
        }
    }
</script>

<style>
</style>
