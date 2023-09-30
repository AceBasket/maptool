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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class JTableColumnHeaderToolTips extends MouseMotionAdapter {
  TableColumn col;
  Map headerColumnToolTips = new HashMap();

  public void setHeaderColumnToolTips(TableColumn col, String tooltip) {
    if (tooltip == null) {
      headerColumnToolTips.remove(col);
    } else {
      headerColumnToolTips.put(col, tooltip);
    }
  }

  @Override
  public void mouseMoved(MouseEvent event) {
    JTableHeader header = (JTableHeader) event.getSource();
    JTable table = header.getTable();
    TableColumnModel colModel = table.getColumnModel();
    int columnIndex = colModel.getColumnIndexAtX(event.getX());
    TableColumn column = null;
    //		String columnName = "";

    if (columnIndex >= 0) {
      column = colModel.getColumn(columnIndex);
    }

    if (column != col) {
      header.setToolTipText((String) headerColumnToolTips.get(column));
      col = column;
    }
  }
}
