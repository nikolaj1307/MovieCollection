package BE;


    public class TMDBMovie {
        private String original_title;
        private String overview;
        private String poster_path;

        public TMDBMovie(String original_title, String overview, String poster_path) {
            this.original_title = original_title;
            this.overview = overview;
            this.poster_path = poster_path;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getOverview() {
            return overview;
        }

        public String getPoster_path() {return poster_path;}
    }


