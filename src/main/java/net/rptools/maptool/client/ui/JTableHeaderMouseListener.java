/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * MapTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.client.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class JTableHeaderMouseListener extends MouseAdapter {
  @FunctionalInterface
  public interface ValueOperator {
    void setValue(TableModel model, boolean state, int row, int column);
  }

  private JTable table;
  private Map<Integer, Boolean> selectAllColumnsIndexes;

  private ValueOperator operator;

  public JTableHeaderMouseListener(
      JTable table, List<Integer> selectAllColumns, ValueOperator setState) {
    this.table = table;
    selectAllColumnsIndexes = new HashMap<>();
    for (Integer index : selectAllColumns) {
      selectAllColumnsIndexes.put(index, false);
    }
    operator = setState;
  }

  @Override
  public void mouseClicked(MouseEvent evt) {
    boolean state = false;
    JTableHeader header = (JTableHeader) evt.getSource();

    TableColumnModel columnModel = header.getColumnModel();
    int viewColumn = columnModel.getColumnIndexAtX(evt.getX());
    TableColumn column = null;

    if (viewColumn >= 0) {
      column = columnModel.getColumn(viewColumn);

      boolean isSelectAllColumn = selectAllColumnsIndexes.containsKey(viewColumn);
      if (isSelectAllColumn) {
        state = !selectAllColumnsIndexes.get(viewColumn);
        selectAllColumnsIndexes.put(viewColumn, state);
        for (int row = 0; row < table.getRowCount(); row++) {
          operator.setValue(table.getModel(), state, row, viewColumn);
        }
      }
    }
  }
}
