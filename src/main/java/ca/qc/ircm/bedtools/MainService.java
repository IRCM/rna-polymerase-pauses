/*
 * Copyright (c) 2017 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.bedtools;

import static ca.qc.ircm.bedtools.AssesPauseSitesCommand.ASSES_PAUSE_SITES_COMMAND;
import static ca.qc.ircm.bedtools.MoveAnnotationsCommand.MOVE_ANNOTATIONS_COMMAND;
import static ca.qc.ircm.bedtools.SetAnnotationsSizeCommand.SET_ANNOTATIONS_SIZE_COMMAND;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Main service.
 */
@Component
public class MainService implements CommandLineRunner {
  private static Logger logger = LoggerFactory.getLogger(MainService.class);
  @Inject
  private BedTransform bedTransform;
  @Inject
  private AssesPauseSites assesPauseSites;
  @Value("${spring.runner.enabled}")
  private boolean runnerEnabled;

  protected MainService() {
  }

  protected MainService(BedTransform bedTransform, AssesPauseSites assesPauseSites,
      boolean runnerEnabled) {
    this.bedTransform = bedTransform;
    this.assesPauseSites = assesPauseSites;
    this.runnerEnabled = runnerEnabled;
  }

  /**
   * Launch sub-program.
   *
   * @param args
   *          command line arguments
   */
  @Override
  public void run(String... args) {
    if (!runnerEnabled) {
      return;
    }

    MainCommand mainCommand = new MainCommand();
    SetAnnotationsSizeCommand setAnnotationSizeCommand = new SetAnnotationsSizeCommand();
    MoveAnnotationsCommand moveAnnotationsCommand = new MoveAnnotationsCommand();
    AssesPauseSitesCommand assesPauseSitesCommand = new AssesPauseSitesCommand();
    JCommander command =
        JCommander.newBuilder().addObject(mainCommand).addCommand(setAnnotationSizeCommand)
            .addCommand(moveAnnotationsCommand).addCommand(assesPauseSitesCommand).build();
    command.setCaseSensitiveOptions(false);
    try {
      command.parse(args);
      if (command.getParsedCommand() == null || mainCommand.help) {
        command.usage();
      } else if (command.getParsedCommand().equals(SET_ANNOTATIONS_SIZE_COMMAND)) {
        if (setAnnotationSizeCommand.help) {
          command.usage(SET_ANNOTATIONS_SIZE_COMMAND);
        } else {
          setAnnotationsSize(setAnnotationSizeCommand);
        }
      } else if (command.getParsedCommand().equals(MOVE_ANNOTATIONS_COMMAND)) {
        if (moveAnnotationsCommand.help) {
          command.usage(MOVE_ANNOTATIONS_COMMAND);
        } else {
          moveAnnotations(moveAnnotationsCommand);
        }
      } else if (command.getParsedCommand().equals(ASSES_PAUSE_SITES_COMMAND)) {
        if (assesPauseSitesCommand.help) {
          command.usage(MOVE_ANNOTATIONS_COMMAND);
        } else {
          assesPauseSites(assesPauseSitesCommand);
        }
      }
    } catch (ParameterException e) {
      System.err.println(e.getMessage() + "\n");
      command.usage();
    }
  }

  private void setAnnotationsSize(SetAnnotationsSizeCommand parameters) {
    logger.debug("Set annotations size to {}", parameters.size);
    try {
      bedTransform.setAnnotationsSize(System.in, System.out, parameters);
    } catch (NumberFormatException e) {
      System.err.println("Could not parse annotation coordinates");
    } catch (IOException e) {
      System.err.println("Could not read input or write to output");
    }
  }

  private void moveAnnotations(MoveAnnotationsCommand parameters) {
    logger.debug("Move annotations by {} bases", parameters.distance);
    try {
      bedTransform.moveAnnotations(System.in, System.out, parameters);
    } catch (NumberFormatException e) {
      System.err.println("Could not parse annotation coordinates");
    } catch (IOException e) {
      System.err.println("Could not read input or write to output");
    }
  }

  private void assesPauseSites(AssesPauseSitesCommand parameters) {
    logger.debug("Asses RNA polymerase pause sites");
    try {
      assesPauseSites.assesPauseSites(System.out, parameters);
    } catch (NumberFormatException e) {
      System.err.println("Could not parse annotation coordinates or score");
    } catch (IOException e) {
      System.err.println("Could not read input or write to output");
    }
  }
}
