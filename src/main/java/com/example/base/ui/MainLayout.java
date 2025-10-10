package com.example.base.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

/**
 * {@code MainLayout} define o layout principal da aplicação Vaadin.
 * <p>
 * Esta classe estende {@link AppLayout} e é responsável por estruturar
 * o conteúdo principal da aplicação, incluindo:
 * <ul>
 *     <li>O cabeçalho (logo e nome da aplicação)</li>
 *     <li>A barra lateral de navegação (SideNav)</li>
 * </ul>
 * <p>
 * O layout segue o padrão “Drawer”, com um menu lateral colapsável.
 */
@Layout
public final class MainLayout extends AppLayout {

    /**
     * Construtor padrão do layout principal.
     * <p>
     * Define a secção principal como o “Drawer” e adiciona o cabeçalho
     * e a barra de navegação lateral.
     */
    MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), new Scroller(createSideNav()));
    }

    /**
     * Cria o cabeçalho da aplicação, composto por um ícone (logo)
     * e o nome da aplicação.
     *
     * @return um componente {@link Div} representando o cabeçalho.
     */
    private Div createHeader() {
        // TODO Substituir pelo logo e nome reais da aplicação
        var appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("App");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        return header;
    }

    /**
     * Cria o menu lateral de navegação da aplicação (SideNav).
     * <p>
     * O conteúdo é gerado dinamicamente com base nas entradas
     * definidas em {@link MenuConfiguration#getMenuEntries()}.
     *
     * @return o componente {@link SideNav} configurado.
     */
    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.addClassNames(Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    /**
     * Cria um item de navegação individual com base nas informações
     * fornecidas por uma instância de {@link MenuEntry}.
     *
     * @param menuEntry objeto que contém o título, caminho e ícone (opcional) do item.
     * @return um {@link SideNavItem} correspondente à entrada do menu.
     */
    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }
}
