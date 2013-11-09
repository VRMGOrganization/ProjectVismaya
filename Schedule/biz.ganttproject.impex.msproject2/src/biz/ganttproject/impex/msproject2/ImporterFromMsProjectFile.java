/*
GanttProject is an opensource project management tool. License: GPL3
Copyright (C) 2010 Dmitry Barashev

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package biz.ganttproject.impex.msproject2;

import java.io.File;
import java.util.List;

import net.sf.mpxj.MPXJException;
import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.gui.NotificationChannel;
import net.sourceforge.ganttproject.importer.Importer;
import net.sourceforge.ganttproject.importer.ImporterBase;
import net.sourceforge.ganttproject.language.GanttLanguage;

public class ImporterFromMsProjectFile extends ImporterBase implements Importer {
  public ImporterFromMsProjectFile() {
    super("impex.msproject2");
  }

  @Override
  public String getFileNamePattern() {
    return "mpp|mpx|xml";
  }

  @Override
  public void run(File selectedFile) {
    try {
      List<String> errors = new ProjectFileImporter(getProject(), getUiFacade().getTaskTree(), selectedFile).run();
      if (!errors.isEmpty()) {
        StringBuilder builder = new StringBuilder();
        for (String message : errors) {
          GPLogger.log(message);
          builder.append("<li>").append(message);
        }
        getUiFacade().showNotificationDialog(NotificationChannel.WARNING,
            GanttLanguage.getInstance().formatText("impex.msproject.importErrorReport", builder.toString()));
      }
    } catch (MPXJException e) {
      getUiFacade().showErrorDialog(e);
    }
  }
}
