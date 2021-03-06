package org.uberfire.ext.layout.editor.client.components;

import org.junit.Test;
import org.uberfire.ext.layout.editor.api.editor.LayoutComponent;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.layout.editor.client.AbstractLayoutEditorTest;
import org.uberfire.ext.layout.editor.client.components.columns.Column;
import org.uberfire.ext.layout.editor.client.components.columns.ColumnWithComponents;
import org.uberfire.ext.layout.editor.client.components.rows.Row;
import org.uberfire.ext.layout.editor.client.components.rows.RowDrop;
import org.uberfire.ext.layout.editor.client.infra.ColumnDrop;
import org.uberfire.ext.layout.editor.client.infra.ColumnResizeEvent;
import org.uberfire.ext.plugin.type.TagsConverterUtil;

import static org.junit.Assert.assertEquals;

public class FullLayoutTest extends AbstractLayoutEditorTest {

    @Test
    public void testFullLayout() throws Exception {

        container.setLayoutName( "A" );
        container.addProperty( TagsConverterUtil.LAYOUT_PROPERTY, "a|" );

        createFirstRow();
        createSecondRow();

        LayoutTemplate layoutTemplate = container.toLayoutTemplate();
        assertEquals( convertLayoutToString( loadLayout( FULL_LAYOUT ) ), convertLayoutToString( layoutTemplate ) );
    }

    private void createSecondRow() {
        Row firstRow = getRowByIndex( 0 );
        container.createRowDropCommand().execute( new RowDrop( new LayoutComponent(
                "org.uberfire.ext.plugin.client.perspective.editor.layout.editor.ScreenLayoutDragComponent" ),
                                                               firstRow.hashCode(),
                                                               RowDrop.Orientation.AFTER ) );
        Column column = getColumnByIndex( getRowByIndex( 1 ), FIRST_COLUMN );
        column.getLayoutComponent().addProperty( "Place Name", "DoraScreen" );
    }


    private void createFirstRow() {
        Column firstRowFirstColumn = createFirstRowFirstColumn();
        createFirstRowSecondColumn( firstRowFirstColumn );
    }

    private void dropTwoInnerColumnsInSecondRow() {
        Row row = getRowByIndex( FIRST_ROW );
        Column secondColumn = getColumnByIndex( row, 1 );

        row.dropCommand().execute( new ColumnDrop( new LayoutComponent(
                "org.uberfire.ext.plugin.client.perspective.editor.layout.editor.ScreenLayoutDragComponent" ),
                                                   secondColumn.hashCode(),
                                                   ColumnDrop.Orientation.DOWN ) );

        ColumnWithComponents columnWithComponents = ( ColumnWithComponents ) getColumnByIndex( row, SECOND_COLUMN );
        Column newColumn = getColumnByIndex( columnWithComponents.getRow(), 1 );
        newColumn.getLayoutComponent().addProperty( "c", "c" );
        newColumn.getLayoutComponent().addProperty( "Place Name", "AnotherScreen" );
    }

    private void resizeColumnsFor_8_4() {
        Row row = getRowByIndex( FIRST_ROW );

        row.resizeColumns( new ColumnResizeEvent( getColumnByIndex( row, 1 ).hashCode(), row.hashCode() ).right() );
        row.resizeColumns( new ColumnResizeEvent( getColumnByIndex( row, 1 ).hashCode(), row.hashCode() ).right() );
    }

    private void createFirstRowSecondColumn( Column firstRowFirstColumn ) {
        Row row = getRowByIndex( FIRST_ROW );


        row.dropCommand().execute( new ColumnDrop(
                new LayoutComponent( "org.uberfire.ext.plugin.client.perspective.editor.layout.editor.HTML" ),
                firstRowFirstColumn.hashCode(),
                ColumnDrop.Orientation.RIGHT ) );
        Column htmlColumn = getColumnByIndex( row, SECOND_COLUMN );
        htmlColumn.getLayoutComponent().addProperty( "HTML_CODE", "c" );


        resizeColumnsFor_8_4();
        dropTwoInnerColumnsInSecondRow();
    }

    private Column createFirstRowFirstColumn() {
        container.createEmptyDropCommand()
                .execute( new RowDrop( new LayoutComponent(
                        "org.uberfire.ext.plugin.client.perspective.editor.layout.editor.ScreenLayoutDragComponent" ),
                                       emptyDropRow.hashCode(),
                                       RowDrop.Orientation.BEFORE ) );

        Column appHomePresenter = getColumnByIndex( getRowByIndex( FIRST_COLUMN ), FIRST_COLUMN );
        appHomePresenter.getLayoutComponent().addProperty( "Place Name", "AppsHomePresenter" );
        return appHomePresenter;
    }
}
