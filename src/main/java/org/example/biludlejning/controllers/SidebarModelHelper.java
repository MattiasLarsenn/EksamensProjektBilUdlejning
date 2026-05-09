package org.example.biludlejning.controllers;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class SidebarModelHelper
{
    public void addSidebarState(Model model, String activeMenu, String activeRole)
    {
        model.addAttribute("activeMenu", activeMenu);
        model.addAttribute("activeRole", activeRole);
    }
}
