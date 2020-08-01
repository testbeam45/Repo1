package githubSearch;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class githubSearch {
    private String repoURI = "https://api.github.com/repos/twbs/bootstrap";
    private String ReleasesURI = "https://api.github.com/repos/twbs/bootstrap/releases";
    private String latestReleaseURI = "https://api.github.com/repos/twbs/bootstrap/releases/latest";

    public githubSearch() {
        getRepoDetails();
        getReleaseDetails();
    }

    //Main
    public static void main(String[] args) {
        try {
            githubSearch githubSearch = new githubSearch();

        } catch (Exception e) {
            System.out.println("Exception found: " + e);
        }
    }

    //API GET method
    public JsonPath getJsonResponse(String uri) {
        baseURI = uri;
        String getResponse =
                given()
                        //.log().all()
                        .when().get()
                        .then()
                        //.log().all()
                        .extract().response().asString();

        return new JsonPath(getResponse);
    }

    //Extract String from Json response
    public String getStringValueOf(String uri, String key) {
        JsonPath js = getJsonResponse(uri);
        return js.get(key);
    }

    //Extract Integer from Json response
    public Integer getIntValueOf(String uri, String key) {
        JsonPath js = getJsonResponse(uri);
        return js.get(key);
    }

    //Method to extract required repo related details
    public void getRepoDetails() {
        String repo = getStringValueOf(repoURI, "name");
        Integer stars = getIntValueOf(repoURI, "stargazers_count");
        System.out.println("Repository Name: " + repo);
        System.out.println("Stars: " + stars);
    }

    //Method to extract required release related details - uses diff endpoints than repo
    public void getReleaseDetails() {
        Integer noOfReleases = getIntValueOf(ReleasesURI, "tag_name.size()");
        System.out.println("Releases: " + noOfReleases);

        String latestRelLnk = getStringValueOf(latestReleaseURI, "html_url");
        System.out.println("Latest Release: " + latestRelLnk);
    }

}
