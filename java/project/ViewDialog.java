package ch20;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;

class ViewDialog extends JDialog {
    private final JTextField tfTitle  = new JTextField(30);
    private final JTextField tfWriter = new JTextField(30);
    private final JTextArea  taContent= new JTextArea(6,30);

    private final BoardDAO dao = new BoardDAO();
    private final int id;
    private boolean dirty=false;

    ViewDialog(Frame owner,int id){
        super(owner,true);
        this.id=id;
        setTitle("게시글 보기");
        setSize(400,280);
        setLocationRelativeTo(owner);

        /* ===== center ===== */
        JPanel c=new JPanel(); c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
        c.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        c.add(row("제목",tfTitle ));
        c.add(row("글쓴이",tfWriter));
        taContent.setLineWrap(true); taContent.setWrapStyleWord(true);
        c.add(row("내용",new JScrollPane(taContent)));
        getContentPane().add(c,BorderLayout.CENTER);

        /* ===== south buttons ===== */
        JPanel s=new JPanel();
        JButton btnEdit=new JButton("수정");
        JButton btnDel =new JButton("삭제");
        JButton btnClose=new JButton("닫기");
        s.add(btnEdit); s.add(btnDel); s.add(btnClose);
        getContentPane().add(s,BorderLayout.SOUTH);

        btnEdit .addActionListener(e->{ edit(); });
        btnDel  .addActionListener(e->{ delete(); });
        btnClose.addActionListener(e->{ dispose(); });

        load();
    }
    private JPanel row(String l,JComponent comp){
        JLabel lab=new JLabel(l,SwingConstants.CENTER);
        lab.setPreferredSize(new Dimension(70,30));
        JPanel p=new JPanel(new BorderLayout());
        p.add(lab,BorderLayout.WEST);
        p.add(comp,BorderLayout.CENTER);
        return p;
    }
    private void load(){
        try{
            BoardDTO d=dao.select(id);
            dao.hitUp(id);
            tfTitle .setText(d.getTitle());  tfTitle .setEditable(false);
            tfWriter.setText(d.getWriter()); tfWriter.setEditable(false);
            taContent.setText(d.getContent()); taContent.setEditable(false);
        }catch(Exception ex){BoardMain.error(ex);}
    }
    private void edit(){
        if("수정".equals((((JButton) SwingUtilities.getRootPane(this)
                .getContentPane().getComponent(1)).getComponent(0)).getToolkit())){
            
            tfTitle .setEditable(true);
            tfWriter.setEditable(true);
            taContent.setEditable(true);
            ((JTextComponent) ((JButton) SwingUtilities.getRootPane(this)
                    .getContentPane().getComponent(1)).getComponent(0))
                    .setText("저장");
        }else{ // 저장
            try{
                BoardDTO d=dao.select(id);
                d.setTitle  (tfTitle .getText().trim());
                d.setWriter (tfWriter.getText().trim());
                d.setContent(taContent.getText().trim());
                dao.update(d); dirty=true; dispose();
            }catch(Exception ex){BoardMain.error(ex);}
        }
    }
    private void delete(){
        int ok=JOptionPane.showConfirmDialog(this,"삭제하시겠습니까?","확인",
                JOptionPane.YES_NO_OPTION);
        if(ok==JOptionPane.YES_OPTION){
            try{ dao.delete(id); dirty=true; dispose();}
            catch(Exception ex){BoardMain.error(ex);}
        }
    }
    boolean isDirty(){return dirty;}
}
