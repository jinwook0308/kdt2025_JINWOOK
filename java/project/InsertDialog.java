package ch20;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

class InsertDialog extends JDialog {
    private final JTextField txtTitle  = new JTextField(30);
    private final JTextField txtWriter = new JTextField(30);
    private final JTextArea  txtContent= new JTextArea(6,30);
    private final BoardDAO dao = new BoardDAO();
    private boolean saved = false;           // 저장 성공 여부

    InsertDialog(Frame owner) {
        super(owner, true);
        setTitle("게시물 작성");
        setSize(400, 280);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        /* ───── Center (3줄) ───── */
        JPanel center = new JPanel();
        center.setBorder(new EmptyBorder(10,10,10,10));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.add(row("제목",   txtTitle ));
        center.add(row("글쓴이", txtWriter));
        center.add(row("내용",   wrap(txtContent)));

        getContentPane().add(center, BorderLayout.CENTER);

        /* ───── South 버튼 ───── */
        JPanel south = new JPanel();
        JButton btnSave = new JButton("저장");
        JButton btnCancel = new JButton("취소");
        south.add(btnSave); south.add(btnCancel);
        getContentPane().add(south, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private JPanel row(String label, JComponent comp){
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setPreferredSize(new Dimension(70,30));
        JPanel p = new JPanel(new BorderLayout());
        p.add(l, BorderLayout.WEST);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }
    private JScrollPane wrap(JTextArea ta){
        ta.setBorder(new EtchedBorder());
        ta.setLineWrap(true); ta.setWrapStyleWord(true);
        return new JScrollPane(ta);
    }

    private void save(){
        try{
            BoardDTO d = new BoardDTO();
            d.setTitle  (txtTitle .getText().trim());
            d.setWriter (txtWriter.getText().trim());
            d.setContent(txtContent.getText().trim());
            if(d.getTitle().isEmpty()||d.getWriter().isEmpty()){
                JOptionPane.showMessageDialog(this,"제목/글쓴이는 필수");
                return;
            }
            dao.insert(d);
            saved = true;
            dispose();
        }catch(Exception ex){ BoardMain.error(ex); }
    }
    boolean isSaved(){ return saved; }
}
