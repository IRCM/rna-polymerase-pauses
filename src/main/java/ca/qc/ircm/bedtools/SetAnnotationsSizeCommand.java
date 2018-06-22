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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;

/**
 * Set annotations' size parameters.
 */
@Parameters(
    separators = " =",
    commandNames = SetAnnotationsSizeCommand.SET_ANNOTATIONS_SIZE_COMMAND,
    commandDescription = "Set annotations' size")
public class SetAnnotationsSizeCommand {
  public static final String SET_ANNOTATIONS_SIZE_COMMAND = "setannotationssize";

  @Parameter(names = { "-h", "-help", "--h", "--help" }, description = "Show help", help = true)
  public boolean help = false;
  @Parameter(
      names = { "-s", "--size" },
      description = "Annotations size",
      required = true,
      validateWith = PositiveInteger.class)
  public Integer size;
  @Parameter(
      names = { "-c", "--changeStart" },
      description = "Change start position instead of end",
      required = false)
  public boolean changeStart;
  @Parameter(
      names = { "-r", "--reverseForNegativeStrand" },
      description = "Change start position instead of end for negative strand."
          + " If --changeStart option is use, change end instead of start",
      required = false)
  public boolean reverseForNegativeStrand;
}
