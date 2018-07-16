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

package ca.qc.ircm.rnapolymerasepauses.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Validates that file exists for specified parameter.
 */
public class FileExistsValidation implements IParameterValidator {
  @Override
  public void validate(String name, String value) throws ParameterException {
    Path path = Paths.get(value);
    if (!Files.exists(path)) {
      throw new ParameterException("File " + value + " does not exists for parameter " + name);
    }
  }
}
