package ch20;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BoardMain extends JFrame {
    private final DefaultTableModel tm =
            new DefaultTableModel(new Object[]{"번호","제목","글쓴이","날짜","조회수"},0){
                @Override public boolean isCellEditable(int r,int c){return false;}
            };
    private final JTable table = new JTable(tm);
    private final BoardDAO dao = new BoardDAO();

    public BoardMain(){
        super("게시판 리스트");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,450); setLocationRelativeTo(null);

        /* 테이블 컬럼 폭 & 렌더러 */
        int[] width={50,250,80,90,60};
        for(int i=0;i<width.length;i++) table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
        CenterRenderer cr=new CenterRenderer();
        for(int i=0;i<table.getColumnCount();i++) table.getColumnModel().getColumn(i).setCellRenderer(cr);

        add(new JScrollPane(table), BorderLayout.CENTER);

        /* 더블클릭: 상세보기 */
        table.addMouseListener(new MouseAdapter(){
            @Override public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    int row=table.getSelectedRow();
                    int id=(int)tm.getValueAt(row,0);
                    ViewDialog vd=new ViewDialog(BoardMain.this,id);
                    vd.setVisible(true);
                    if(vd.isDirty()) refresh();
                }
            }
        });

        /* 하단 '추가' 버튼 */
        JButton btnAdd=new JButton("추가");
        btnAdd.addActionListener(e->{
            InsertDialog dlg=new InsertDialog(this);
            dlg.setVisible(true);
            if(dlg.isSaved()) refresh();
        });
        JPanel south=new JPanel(); south.add(btnAdd);
        add(south,BorderLayout.SOUTH);

        refresh();
        setVisible(true);
    }

    /** DB → JTable */
    private void refresh(){
        try{
            tm.setRowCount(0);
            List<BoardDTO> list = dao.selectAll();
            DateTimeFormatter fmt=DateTimeFormatter.ofPattern("yyyy.MM.dd");
            for(BoardDTO d:list){
                tm.addRow(new Object[]{
                        d.getId(), d.getTitle(), d.getWriter(),
                        fmt.format(d.getRegDate()), d.getHits()
                });
            }
        }catch(Exception ex){error(ex);}
    }

    /** 셀 가운데 정렬 */
    static class CenterRenderer extends JLabel implements TableCellRenderer{
        @Override public Component getTableCellRendererComponent(
                JTable t,Object v,boolean sel,boolean f,int r,int c){
            setText(v.toString()); setHorizontalAlignment(CENTER);
            setOpaque(true); setBackground(sel?Color.YELLOW:Color.WHITE);
            return this;
        }
    }
    static void error(Exception ex){
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null,ex.getMessage(),"오류",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args){
        try{ DBUtil.init(); }catch(Exception e){ error(e); return; }
        SwingUtilities.invokeLater(BoardMain::new);
    }
}
