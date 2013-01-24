package ee.uitest;

import com.codeborne.selenide.junit.ScreenShooter;
import ee.era.hangman.Launcher;
import ee.era.hangman.model.Word;
import ee.era.hangman.model.Words;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import static com.codeborne.selenide.junit.ScreenShooter.failedTests;
import static ee.era.hangman.di.DependencyInjection.wire;

public abstract class AbstractHangmanTest {
  @Rule
  public ScreenShooter makeScreenshotOnFailure = failedTests().succeededTests();

  private static Launcher launcher;

  @BeforeClass
  public static void startServer() throws Exception {
    wire(Words.class, WordsMock.class);
    launcher = new Launcher(8080);
    launcher.run();
  }

  @AfterClass
  public static void stopServer() {
    if (launcher != null) {
      launcher.stop();
      launcher = null;
    }
  }

  public static class WordsMock extends Words {
    @Override
    public Word getRandomWord(String language) {
      if ("ru".equals(language))
        return new Word("дом", "гвоздь");
      if ("et".equals(language))
        return new Word("maja", "nael");
      return new Word("house", "sofa");
    }
  }

  public static void main(String[] args) throws Exception {
    startServer();
  }
}